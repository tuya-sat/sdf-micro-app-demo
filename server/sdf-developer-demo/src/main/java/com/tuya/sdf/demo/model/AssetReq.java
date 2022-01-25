package com.tuya.sdf.demo.model;

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

    private String parentAssetId;

    private String asset_name;
}
