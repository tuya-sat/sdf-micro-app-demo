package com.tuya.sdf.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/27
 */
@Data
public class AssetAddReq implements Serializable {

    private static final long serialVersionUID = -4413618452512443438L;

    @JsonProperty("name")
    private String name;

    @JsonProperty("meta_id")
    private String metaId;

    @JsonProperty("parent_asset_id")
    private String parentAssetId;
}
