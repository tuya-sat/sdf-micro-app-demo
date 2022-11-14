package com.tuya.sdf.demo.convert;


import com.tuya.sdf.demo.model.Asset;
import com.tuya.sdf.demo.model.AssetDTO;
import com.tuya.sdf.demo.model.AssetVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * <p> TODO
 *
 * @author @author developer@tuya.com
 * @since 2021/4/20 11:32 下午
 */
@Mapper
public interface AssetConvertor {
    AssetConvertor $ = Mappers.getMapper(AssetConvertor.class);

    AssetDTO toAssetDTO(Asset asset);

    List<AssetDTO> toAssetDTOList(List<Asset> assetList);

    List<AssetVO> toAssetVOList(List<AssetDTO> assetDTOList);


}
