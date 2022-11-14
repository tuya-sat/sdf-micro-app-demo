package com.tuya.sdf.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/9
 */
@Getter
@Setter
@ToString
public class Asset implements Serializable {

    private static final long serialVersionUID = 3658227130368171924L;

    /**
     * 资产Id
     */
    @JsonProperty("asset_id")
    private String assetId;

    /**
     * 资产名
     */
    @JsonProperty("asset_name")
    private String assetName;

    /**
     * 资产全名
     */
    @JsonProperty("asset_full_name")
    private String assetFullName;

    /**
     * 父资产id
     */
    @JsonProperty("parent_asset_id")
    private String parentAssetId;

    /**
     * 设备ID
     */
    @JsonProperty("device_id")
    private String deviceId;
    /**
     * 层级号
     */
    @JsonProperty("level")
    private Integer level;
}
