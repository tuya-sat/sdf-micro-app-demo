package com.tuya.sdf.demo.ability;

import com.tuya.sdf.demo.model.IdaasSpaceRsp;
import com.tuya.sdf.demo.model.SpaceApplyReq;

/**
 * @description
 * @author developer@tuya.com
 * @date 2021/05/31
 */
public interface SpaceAbility {
    /**
     * 申请权限空间
     * @param spaceApplyRequest 权限空间申请参数
     * @return 申请是否成功
     * */
    String applySpace(SpaceApplyReq spaceApplyRequest);

    IdaasSpaceRsp querySpace(String spaceGroup, String spaceCode);
}
