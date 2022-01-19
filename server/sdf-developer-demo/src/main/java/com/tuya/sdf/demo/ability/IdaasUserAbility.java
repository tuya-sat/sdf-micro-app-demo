package com.tuya.sdf.demo.ability;


import com.tuya.sdf.demo.model.IdaasUser;
import com.tuya.sdf.demo.model.IdaasUserPageReq;
import com.tuya.sdf.demo.model.IdaasUserPageResult;

/**
 * idaas的用户能力，区分iot平台的用户能力
 *
 * @author developer@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface IdaasUserAbility {

    IdaasUserPageResult<IdaasUser> queryUserPage(String spaceId, IdaasUserPageReq req);
}
