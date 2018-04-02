package com.scd.mweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CommodityPo;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.feign.FCommodity;
import com.scd.mweb.pojo.util.CommodityUtil;
import com.scd.mweb.pojo.vo.CommodityAssortVo;
import com.scd.mweb.pojo.vo.CommodityRecommendVo;
import com.scd.mweb.pojo.vo.CommodityVo;
import com.scd.sdk.util.pojo.Return;

@Service
public class CommodityService {
	
	@Autowired
	private FCommodity fCommodity;

	public Return<CommodityVo> getOne(long id) {
		Return<CommodityPo> ret = fCommodity.getOne(id);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		CommodityVo vo = CommodityUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(vo);
	}
	
	public Return<List<CommodityVo>> getList(long shopId, int type, int page, int size) {
		Return<List<CommodityPo>> ret = fCommodity.getPageIncludeAttr(shopId, type, page, size);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		List<CommodityVo> voList = CommodityUtil.change(ret.getData());
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(voList);
	}

	public Return<List<CommodityAssortVo>> getAssort(long id) {
		Return<List<CommodityPo>> ret = fCommodity.getAssort(id);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		List<CommodityPo> poList = ret.getData();
		List<CommodityAssortVo> voList = CommodityUtil.changeAssort(poList);
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(voList);
	}

	public Return<List<CommodityRecommendVo>> getRecommend(long id) {
		Return<List<CommodityPo>> ret = fCommodity.getRecommend(id);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		List<CommodityPo> poList = ret.getData();
		List<CommodityRecommendVo> voList = CommodityUtil.changeRecommend(poList);
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(voList);
	}
}
