package com.tuya.sdf.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 已经授权的资产
 * @auther: developer@tuya.com
 * @date: 2021/04/20
 **/
@Data
@AllArgsConstructor
public class AuthorizedAsset implements Serializable {
    private static final long serialVersionUID = -3350891636365757991L;
    /**
     * 资产ID
     */
    @JsonProperty("asset_id")
    private String assetId;

    /**
     * 父资产ID
     */
    @JsonProperty("parent_asset_id")
    private String parentAssetId;

    /**
     * 资产名称
     */
    @JsonProperty("asset_name")
    private String assetName;

    /**
     * 层级号
     */
    @JsonProperty("level")
    private Integer level;


}
