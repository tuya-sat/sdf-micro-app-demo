package com.tuya.sdf.demo.connector;

import com.tuya.connector.api.annotations.*;
import com.tuya.sdf.demo.ability.IdaasUserAbility;
import com.tuya.sdf.demo.model.IdaasUser;
import com.tuya.sdf.demo.model.IdaasUserPageReq;
import com.tuya.sdf.demo.model.IdaasUserPageResult;

/**
 * @author developer@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface IdaasUserConnector extends IdaasUserAbility {

    @POST("/v1.0/iot-03/idaas/spaces/{space_id}/page-user")
    IdaasUserPageResult<IdaasUser> queryUserPage(@Path("space_id") String spaceId, @Body IdaasUserPageReq req);
}
