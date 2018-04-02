package com.scd.admin.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FCommodity;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.CommodityAttrParam;
import com.scd.joggle.pojo.param.CommodityParam;
import com.scd.joggle.pojo.param.CommoditySelectParam;
import com.scd.joggle.pojo.po.CommodityAttrPo;
import com.scd.joggle.pojo.po.CommodityOriPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HCommodity implements FCommodity {


	@Override
	public Return<PageInfo<CommodityAttrPo>> getAttrPage(long commodityId, int page, int size) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<CommodityAttrPo> attrAdd(CommodityAttrParam param) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<CommodityAttrPo> attrEdit(CommodityAttrParam param) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Long> attrDel(long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<Long>> attrBatchDel(List<Long> idList) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<PageInfo<CommodityOriPo>> getPage(CommoditySelectParam param) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<CommodityOriPo> add(CommodityParam param) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<CommodityOriPo> edit(CommodityParam param) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Long> del(long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<Long>> batchDel(List<Long> idList) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<CommodityOriPo> setState(long id, int state) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
