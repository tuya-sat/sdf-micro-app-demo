package com.tuya.sdf.demo.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.Query;
import com.tuya.sdf.demo.ability.SpaceAbility;
import com.tuya.sdf.demo.model.IdaasSpaceRsp;
import com.tuya.sdf.demo.model.SpaceApplyReq;

/**
 * @author developer@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface SpaceConnector extends SpaceAbility {

    @Override
    @POST("/v1.0/iot-03/idaas/spaces")
    String applySpace(@Body SpaceApplyReq spaceApplyRequest);

    @Override
    @GET("/v1.0/iot-03/idaas/spaces")
    IdaasSpaceRsp querySpace(@Query("spaceGroup") String spaceGroup,
                             @Query("spaceCode") String spaceCode);
}
