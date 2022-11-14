package com.tuya.sdf.demo.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tuya.sdf.demo.ability.SpaceAbility;
import com.tuya.sdf.demo.model.IdaasSpaceRsp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 哲也
 * @className SpaceService
 * @desc
 * @since 2022/1/25
 */
@Slf4j
@Service
public class SpaceService {

    public static final String SPACE_CODE_DEFAULT = "MICRO_APP_DEFAULT";
    public static Cache<String, String> CACHE = Caffeine.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS)
            .maximumSize(100)
            .build();

    @Autowired
    private SpaceAbility spaceAbility;

    /**
     * @return java.lang.String
     * @description 获取spaceId
     * @params [projectCode, merchantCode]
     * @creator liuci
     * @createTime 2021/12/25
     */
    public String getSpaceId(String projectCode, String merchantCode) {
        if (StringUtils.isBlank(merchantCode)) {
            merchantCode = SPACE_CODE_DEFAULT;
        }
        String key = getSpaceIdKey(projectCode, merchantCode);
        String spaceId = CACHE.getIfPresent(key);
        if (StringUtils.isNotBlank(spaceId)) {
            return spaceId;
        }
        IdaasSpaceRsp idaasSpaceOuterRsp = spaceAbility.querySpace(projectCode, merchantCode);
        if (Objects.nonNull(idaasSpaceOuterRsp)) {
            spaceId = idaasSpaceOuterRsp.getSpaceId();
            setSpaceId(projectCode, merchantCode, spaceId);
        }else {//兜底机制
            log.info("触发idaas兜底机制 projectCode:{}", projectCode);
            IdaasSpaceRsp idaasSpaceRsp = spaceAbility.querySpace(projectCode, SPACE_CODE_DEFAULT);
            spaceId = idaasSpaceRsp.getSpaceId();
            setSpaceId(projectCode, SPACE_CODE_DEFAULT, spaceId);
        }
        return spaceId;
    }

    private void setSpaceId(String projectCode, String merchantCode, String spaceId) {
        String key = getSpaceIdKey(projectCode, merchantCode);
        CACHE.put(key, spaceId);
    }

    private String getSpaceIdKey(String projectCode, String merchantCode) {
        return "space_id:" + projectCode + merchantCode;
    }
}
