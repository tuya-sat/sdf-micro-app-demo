package com.tuya.sdf.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description  TODO
 *
 * @author Chyern
 * @date 2021/3/15
 */
@Data
public class AssetModifyReq implements Serializable {

    private static final long serialVersionUID = 4046817704993815783L;

    @JsonProperty("name")
    private String name;

    @JsonProperty("meta_id")
    private String metaId;

}
