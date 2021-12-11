package com.tuya.sdf.demo.controller.highway;

import com.tuya.highway.common.annotation.RequestParam;
import com.tuya.highway.common.annotation.RestMapping;
import com.tuya.highway.common.constants.PositionEnum;
import com.tuya.highway.common.constants.RequestMethod;
import com.tuya.sdf.demo.model.AssetReq;
import com.tuya.sdf.demo.model.AssetVO;
import com.tuya.sdf.starter.model.response.SdfResponse;

import java.util.List;

/**
 * @author 哲也
 * @className AssetApiInterface
 * @desc
 * @since 2021/11/19
 */
public interface AssetApiInterface {

    /**
     * 新增资产
     *
     * @param req
     * @return
     */
    @RestMapping(value = "/v1.0/assets", desc = "新增资产", method = RequestMethod.POST)
    String addAsset(
            @RequestParam(position = PositionEnum.BODY, desc = "资产信息", required = true, example = "资产信息参数")
                    AssetReq req);


    /**
     * 修改资产
     *
     * @param assetId
     * @param criteria
     * @return
     */
    @RestMapping(value = "/v1.0/assets/{asset_id}", desc = "修改资产", method = RequestMethod.PUT)
    Boolean updateAsset(
            @RequestParam(position = PositionEnum.URI, desc = "资产id", required = true, example = "1234")
                    String assetId,
            @RequestParam(position = PositionEnum.BODY, desc = "资产信息", required = true, example = "资产信息参数")
                    AssetReq criteria);


    /**
     * 删除资产
     *
     * @param assetId
     * @return
     */
    @RestMapping(value = "/v1.0/assets/{asset_id}", desc = "删除资产", method = RequestMethod.DELETE)
    Boolean deleteAsset(
            @RequestParam(position = PositionEnum.URI, desc = "资产id", required = true, example = "1234")
                    String assetId);

    /**
     * 根据资产id获取子资产列表
     *
     * @param assetId
     * @return
     */
    @RestMapping(value = "/v1.0/assets/{asset_id}", desc = "根据资产id获取子资产列表", method = RequestMethod.GET)
    List<AssetVO> getSubAssetByFatherId(
            @RequestParam(position = PositionEnum.URI, desc = "资产id", required = true, example = "1234")
                    String assetId);
}
