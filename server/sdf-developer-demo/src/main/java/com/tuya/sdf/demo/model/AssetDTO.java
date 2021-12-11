package com.tuya.sdf.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/9
 */
@Getter
@Setter
@ToString
public class AssetDTO implements Serializable {

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
     * 父资产Id
     */
    @JsonProperty("parent_asset_id")
    private String parentAssetId;

    /**
     * 子资产数量
     * openapi返回可能为null
     */
    @JsonProperty("child_asset_count")
    private Integer childAssetCount;

    /**
     * 子设备数量
     */
    @JsonProperty("child_device_count")
    private Integer childDeviceCount;

    /**
     * 层级号
     */
    @JsonProperty("level")
    private Integer level;

    /**
     * 是否有权限 true-有 false-无
     */
    @JsonProperty("is_authorized")
    private Boolean isAuthorized;

    /**
     * 子资产
     */
    @JsonProperty("sub_assets")
    private List<AssetDTO> subAssets;
}
