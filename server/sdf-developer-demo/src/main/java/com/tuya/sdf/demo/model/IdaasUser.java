package com.tuya.sdf.demo.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author developer@tuya.com
 * @description
 * @date 2021/05/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class IdaasUser {

    /**
     * 用户uid
     */
    String uid;

    /**
     * 外部用户名
     */
    String username;

    /**
     * 备注
     */
    String remark;

    String spaceId;

    String roleCode;

    String roleName;

    String gmt_create;

}
