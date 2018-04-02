package com.scd.mweb.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.mweb.constant.Constant;
import com.scd.mweb.feign.FCommodity;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CommodityPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HCommodity implements FCommodity{

	@Override
	public Return<CommodityPo> getOne(long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<CommodityPo>> getPageIncludeAttr(long shopId, int type, int page, int size) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<CommodityPo>> getAssort(long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<CommodityPo>> getRecommend(long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
