package com.tuya.sdf.demo.controller;

import com.tuya.sdf.demo.controller.highway.AssetApiInterface;
import com.tuya.sdf.demo.convert.AssetConvertor;
import com.tuya.sdf.demo.model.AssetReq;
import com.tuya.sdf.demo.model.AssetVO;
import com.tuya.sdf.demo.service.AssetService;
import com.tuya.sdf.starter.model.response.SdfResponse;
import com.tuya.sdf.starter.util.SdfContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 哲也
 * @className AssetController
 * @desc
 * @since 2021/11/19
 */
@Slf4j
@RequestMapping("/v1.0/assets")
@RestController
public class AssetController implements AssetApiInterface {

    @Autowired
    private AssetService assetService;

    @Override
    @PostMapping
    public String addAsset(@RequestBody AssetReq req) {
        return assetService.addAsset(req.getAssetName(), req.getParentAssetId());
    }

    @Override
    @PutMapping(value = "/{asset_id}")
    public Boolean updateAsset(@PathVariable("asset_id") String assetId, @RequestBody AssetReq req) {
        return assetService.updateAsset(assetId, req.getAssetName());
    }

    @Override
    @DeleteMapping(value = "/{asset_id}")
    public Boolean deleteAsset(@PathVariable("asset_id") String assetId) {
        return assetService.deleteAsset(assetId);
    }

    @Override
    @GetMapping(value = "/{asset_id}")
    public List<AssetVO> getSubAssetByFatherId(@PathVariable("asset_id") String assetId) {
        return AssetConvertor.$
                .toAssetVOList(assetService.getChildAssetListBy(SdfContextHolder.getUser().getUserId(), assetId));
    }
}
