package com.tuya.sdf.demo.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author developer@tuya.com
 * @description
 * @date 2021/06/07
 */
@Data
@Builder
public class IdaasSpaceRsp {
    String spaceGroup;
    String spaceId;
    String owner;
    String spaceCode;
}
