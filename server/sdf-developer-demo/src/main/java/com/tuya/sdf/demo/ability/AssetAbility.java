package com.tuya.sdf.demo.ability;


import com.tuya.connector.open.api.model.PageResult;
import com.tuya.connector.open.api.model.PageResultWithTotal;
import com.tuya.sdf.demo.model.*;

import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/11
 */
public interface AssetAbility {

    String addAsset(AssetAddReq request);

    Boolean modifyAsset(String assetId, AssetModifyReq body);

    Boolean deleteAsset(String assetId);

    List<Asset> selectAssets(String assetIds);

    PageResult<Asset> selectChildAssets(String assetId, String lastRowKey, String pageSize);

    PageResultWithTotal<AuthorizedAsset> pageListAuthorizedAssets(String assetId, Integer pageNo, Integer pageSize);

    Boolean authorized(AssetAuthorizationRequest assetAuthorizationRequest);
}
