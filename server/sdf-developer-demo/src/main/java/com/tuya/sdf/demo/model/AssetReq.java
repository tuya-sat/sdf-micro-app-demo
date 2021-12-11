package com.tuya.sdf.demo.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 哲也
 * @className AssetReq
 * @desc
 * @since 2021/11/19
 */
@Data
public class AssetReq implements Serializable {

    @Schema(required = false, description = "父资产ID", example = "id1")
    private String parentAssetId;

    @Schema(required = true, description = "资产名称", example = "资产1")
    private String assetName;
}
