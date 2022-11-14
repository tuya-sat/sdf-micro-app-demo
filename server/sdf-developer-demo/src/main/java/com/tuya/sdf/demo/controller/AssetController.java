package com.tuya.sdf.demo.controller;

import com.tuya.sdf.demo.convert.AssetConvertor;
import com.tuya.sdf.demo.model.AssetReq;
import com.tuya.sdf.demo.model.AssetVO;
import com.tuya.sdf.demo.model.Response;
import com.tuya.sdf.demo.service.AssetService;
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
public class AssetController {

    @Autowired
    private AssetService assetService;

    @PostMapping
    public Response<String> addAsset(@RequestBody AssetReq req) {
        return Response.success(assetService.addAsset(req.getAsset_name(), req.getParentAssetId()));
    }

    @PutMapping(value = "/{asset_id}")
    public Response<Boolean> updateAsset(@PathVariable("asset_id") String assetId, @RequestBody AssetReq req) {
        return Response.success(assetService.updateAsset(assetId, req.getAsset_name()));
    }

    @DeleteMapping(value = "/{asset_id}")
    public Response<Boolean> deleteAsset(@PathVariable("asset_id") String assetId) {
        return Response.success(assetService.deleteAsset(assetId));
    }

    @GetMapping(value = "/{asset_id}")
    public Response<List<AssetVO>> getSubAssetByFatherId(@PathVariable("asset_id") String assetId) {
        return Response.success((AssetConvertor.$
                .toAssetVOList(assetService.getChildAssetListBy(SdfContextHolder.getUser().getUserId(), assetId))));
    }
}
