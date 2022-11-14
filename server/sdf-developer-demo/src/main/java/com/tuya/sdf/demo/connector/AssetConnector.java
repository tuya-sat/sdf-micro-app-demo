package com.tuya.sdf.demo.connector;


import com.tuya.connector.api.annotations.*;
import com.tuya.connector.open.api.model.PageResult;
import com.tuya.connector.open.api.model.PageResultWithTotal;
import com.tuya.sdf.demo.ability.AssetAbility;
import com.tuya.sdf.demo.model.*;

import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/10
 */
public interface AssetConnector extends AssetAbility {

    @Override
    @POST("/v1.0/iot-02/assets")
    String addAsset(@Body AssetAddReq request);

    @Override
    @PUT("/v1.0/iot-02/assets/{asset_id}")
    Boolean modifyAsset(@Path("asset_id") String assetId, @Body AssetModifyReq body);

    @Override
    @DELETE("/v1.0/iot-02/assets/{asset_id}")
    Boolean deleteAsset(@Path("asset_id") String assetId);

    @Override
    @GET("/v1.0/iot-02/assets")
    List<Asset> selectAssets(@Query("asset_ids") String assetIds);

    @Override
    @GET("/v1.0/iot-02/assets/{asset_id}/sub-assets")
    PageResult<Asset> selectChildAssets(@Path("asset_id") String assetId, @Query("last_row_key") String lastRowKey,
                                        @Query("page_size") String pageSize);

    @Override
    @GET("/v1.0/iot-03/users/{uid}/assets")
    PageResultWithTotal<AuthorizedAsset> pageListAuthorizedAssets(@Path("uid") String assetId,
                                                                  @Query("pageNo") Integer pageNo,
                                                                  @Query("pageSize") Integer pageSize);

    @Override
    @POST("/v1.0/iot-03/assets/actions/user-authorized")
    Boolean authorized(@Body AssetAuthorizationRequest assetAuthorizationRequest);
}
