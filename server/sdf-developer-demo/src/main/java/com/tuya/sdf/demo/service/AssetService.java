package com.tuya.sdf.demo.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.collect.Maps;
import com.tuya.connector.open.api.model.PageResult;
import com.tuya.connector.open.api.model.PageResultWithTotal;
import com.tuya.sdf.demo.ability.AssetAbility;
import com.tuya.sdf.demo.ability.IdaasUserAbility;
import com.tuya.sdf.demo.ability.SpaceAbility;
import com.tuya.sdf.demo.convert.AssetConvertor;
import com.tuya.sdf.demo.model.*;
import com.tuya.sdf.starter.util.SdfContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.shade.org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 哲也
 * @className AssetService
 * @desc
 * @since 2021/11/19
 */
@Slf4j
@Service
public class AssetService {

    private static final int PAGE_NO = 1;
    private static final int PAGE_SIZE = 100;
    private static final String ASSET_TOP_ID = "-1";
    private final AtomicInteger atomicInteger = new AtomicInteger(1);
    private final int corePoolSize = 50;
    private final int maximumPoolSize = 50;
    private final long keepAliveTime = 60;
    private final TimeUnit unit = TimeUnit.SECONDS;
    private final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
    private final ThreadFactory threadFactory = r -> new Thread(r, "asset-pool-" + atomicInteger.getAndIncrement());
    private final RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
    private final ExecutorService executorService = TtlExecutors.getTtlExecutorService(new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler));

    @Autowired
    private AssetAbility assetAbility;
    @Autowired
    private IdaasUserAbility idaasUserAbility;
    @Autowired
    private SpaceService spaceService;

    public String addAsset(String assetName, String parentAssetId) {
        String spaceId = spaceService.getSpaceId(SdfContextHolder.getSdfSaas().getProjectCode(), SdfContextHolder.getUser().getTenantCode());
        AssetAddReq request = new AssetAddReq();
        request.setName(assetName);
        request.setParentAssetId(parentAssetId);
        String assetId = assetAbility.addAsset(request);

        if (StringUtils.isNotBlank(parentAssetId) && !ASSET_TOP_ID.equals(parentAssetId)) {
            log.info("Non-primary assets, no need to authorize admin。assetId={}，parentAssetId={}", assetId, parentAssetId);
        } else {
            authorizedAssetWithoutToChildren(spaceId, assetId, SdfContextHolder.getUser().getUserId());
        }

        return assetId;
    }

    public boolean updateAsset(String assetId, String assetName) {
        AssetModifyReq request = new AssetModifyReq();
        request.setName(assetName);
        return assetAbility.modifyAsset(assetId, request);
    }

    public boolean deleteAsset(String assetId) {
        return assetAbility.deleteAsset(assetId);
    }

    public List<AssetDTO> getChildAssetListBy(String userId, String assetId) {
        List<Asset> assetList = getChildAssetsBy(assetId);
        if (CollectionUtils.isEmpty(assetList)) {
            return Lists.newArrayList();
        }

        List<AssetDTO> result = Lists.newArrayList();
        // 资产ID，资产
        Map<String, AssetDTO> idMap = new ConcurrentHashMap<>();
        // 资产ID，资产ID
        Map<String, String> authMap;
        authMap = assetList.stream().collect(Collectors.toMap(Asset::getAssetId, Asset::getAssetId));
        if (StrUtil.isNotBlank(userId)) {
            // 查询用户user授权的资产
            List<String> authList = listAuthorizedAssetIds(userId);
            if (!CollectionUtils.isEmpty(authList)) {
                authMap.clear();
                authMap.putAll(authList.stream().collect(Collectors.toMap(e -> e, e -> e)));
            } else {
                return result;
            }
        }
        // 子资产列表遍历，只保留授权过的资产
        for (Asset asset : assetList) {
            if (!authMap.containsKey(asset.getAssetId())) {
                continue;
            }
            AssetDTO assetDTO = AssetConvertor.$.toAssetDTO(asset);
            String assetDtoId = assetDTO.getAssetId();
            idMap.put(assetDtoId, assetDTO);
            result.add(assetDTO);
        }
        int size = idMap.size();
        CountDownLatch c1 = new CountDownLatch(size);

        //资产ID，子资产数量
        Map<String, Integer> idAssetCountMap = Maps.newConcurrentMap();
        for (String id : idMap.keySet()) {

            assert executorService != null;
            executorService.execute(() -> {
                try {
                    List<Asset> subList = getChildAssetsBy(id);
                    if (!CollectionUtils.isEmpty(subList)) {
                        List<Asset> tempList =
                                subList.stream().filter(e -> authMap.containsKey(e.getAssetId())).collect(Collectors.toList());
                        idAssetCountMap.put(id, CollectionUtils.isEmpty(tempList) ? 0 : tempList.size());
                    }
                } catch (Exception e) {
                    log.warn("getSubAssetByFatherId#getChildAssetListBy,assetCount  exception: " + e.getMessage(), e);
                } finally {
                    c1.countDown();
                }
            });
        }

        //-------等待线程池中多个远程API调用完成-------
        try {
            c1.await(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("getSubAssetByFatherId#getChildAssetListBy-CountDownLatch-c1,InterruptedException:{}", e.getMessage());
        }

        //设置子资产数量
        for (Map.Entry<String, AssetDTO> entry : idMap.entrySet()) {
            entry.getValue().setChildAssetCount(idAssetCountMap.get(entry.getKey()));
        }

        return result;
    }


    private List<Asset> getChildAssetsBy(String assetId) {
        List<Asset> assets = Lists.newArrayList();
        final String pageSize = "100";
        boolean hasNext = true;
        String lastRowKey = "";
        while (hasNext) {
            PageResult<Asset> childAssetsBy = assetAbility.selectChildAssets(assetId, lastRowKey, pageSize);
            hasNext = childAssetsBy.getHasNext();
            lastRowKey = childAssetsBy.getLastRowKey();
            assets.addAll(childAssetsBy.getList());
        }
        return assets;
    }


    /**
     * 根据用户ID 获取授权后的资产
     *
     * @param userId
     * @return
     */
    private List<String> listAuthorizedAssetIds(String userId) {
        log.info("query user userId [{}] Authorized assetsIDs", userId);
        List<String> authorizedAssetIds = new ArrayList<>();

        boolean hasMore = true;
        int pageNo = PAGE_NO;
        while (hasMore) {
            PageResultWithTotal<AuthorizedAsset> pageResult = assetAbility.pageListAuthorizedAssets(userId, pageNo, PAGE_SIZE);
            hasMore = pageResult.getHasMore() != null && pageResult.getHasMore();
            List<AuthorizedAsset> authorizedAsset = pageResult.getList();
            List<String> collect = authorizedAsset.stream().map(AuthorizedAsset::getAssetId).collect(Collectors.toList());
            authorizedAssetIds.addAll(collect);
            pageNo++;
        }
        log.info("return authorizedAssetIds is [{}]", authorizedAssetIds);
        return authorizedAssetIds;
    }

    /**
     * 对资产授权
     *
     * @param assetId
     * @param userId
     */
    private void authorizedAssetWithoutToChildren(String spaceId, String assetId, String userId) {
        log.info("start asset [{}] Authorize to user [{}]", assetId, userId);
        AssetAuthorizationRequest assetAuthorizationRequest = new AssetAuthorizationRequest(userId, assetId, false);
        Boolean result = assetAbility.authorized(assetAuthorizationRequest);
        if (Objects.isNull(result) || !result.booleanValue()) {
            log.info("asset [{}] Authorize to user [{}] failure", assetId, userId);
        } else {
            log.info("asset [{}] Authorize to user [{}] success", assetId, userId);
        }
        //给系统管理员授权
        List<IdaasUser> idaasUsers = queryAdmins(spaceId);
        if (!CollectionUtils.isEmpty(idaasUsers)) {
            for (IdaasUser idaasUser : idaasUsers) {
                assetAuthorizationRequest.setUid(idaasUser.getUid());
                result = result && assetAbility.authorized(assetAuthorizationRequest);
            }
        }
    }

    private List<IdaasUser> queryAdmins(String spaceId) {
        IdaasUserPageResult<IdaasUser> pageResult = idaasUserAbility.queryUserPage(spaceId, IdaasUserPageReq.builder()
                .roleCode("admin")
                .pageNum(1)
                .pageSize(100)
                .build());
        if (pageResult != null && pageResult.getTotalCount() > 0) {
            return pageResult.getResults();
        }
        return new ArrayList<>();
    }

}
