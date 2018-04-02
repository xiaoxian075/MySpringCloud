package com.scd.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FCommodity;
import com.scd.admin.pojo.util.CommodityUtil;
import com.scd.admin.pojo.vo.CommodityAttrVo;
import com.scd.admin.pojo.vo.CommodityVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.CommodityAttrParam;
import com.scd.joggle.pojo.param.CommodityParam;
import com.scd.joggle.pojo.param.CommoditySelectParam;
import com.scd.joggle.pojo.po.CommodityAttrPo;
import com.scd.joggle.pojo.po.CommodityOriPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class CommodityService {
	
	@Autowired
	private FCommodity fCommodity;

	public Return<PageInfo<CommodityVo>> list(CommoditySelectParam param) {
		Return<PageInfo<CommodityOriPo>> ret = fCommodity.getPage(param);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		PageInfo<CommodityOriPo> pageInfo = ret.getData();
		List<CommodityOriPo> oriPoList = pageInfo.getList();
		List<CommodityVo> voList = CommodityUtil.change(oriPoList);
		
		PageInfo<CommodityVo> kdPage = new PageInfo<CommodityVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);
		
		return Constant.createReturn(kdPage);
	}

	public Return<CommodityVo> add(CommodityParam param) {
		Return<CommodityOriPo> ret = fCommodity.add(param);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		CommodityVo vo = CommodityUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(vo);
	}

	public Return<CommodityVo> edit(CommodityParam param) {
		Return<CommodityOriPo> ret = fCommodity.edit(param);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		CommodityVo vo = CommodityUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(vo);
	}

	public Return<Long> del(long id) {
		Return<Long> ret = fCommodity.del(id);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		long returnId = ret.getData();
		return Constant.createReturn(returnId);
	}

	public Return<List<Long>> batchDel(List<Long> idList) {
		Return<List<Long>> ret = fCommodity.batchDel(idList);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ErrorCom.FEIGN_ERROR);
		}
		List<Long> returnListId = ret.getData();
		return Constant.createReturn(returnListId);
	}

	public Return<CommodityVo> setState(long id, int state) {
		Return<CommodityOriPo> ret = fCommodity.setState(id, state);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		CommodityVo vo = CommodityUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(vo);
	}

	

	
	public Return<PageInfo<CommodityAttrVo>> attrList(long commodityId, int page, int size) {
		Return<PageInfo<CommodityAttrPo>> ret = fCommodity.getAttrPage(commodityId, page, size);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		PageInfo<CommodityAttrPo> pageInfo = ret.getData();
		List<CommodityAttrPo> attrPoList = pageInfo.getList();
		List<CommodityAttrVo> voList = CommodityUtil.changeAttr(attrPoList);
		
		PageInfo<CommodityAttrVo> kdPage = new PageInfo<CommodityAttrVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);
		
		return Constant.createReturn(kdPage);
	}

	public Return<CommodityAttrVo> attrAdd(CommodityAttrParam param) {
		Return<CommodityAttrPo> ret = fCommodity.attrAdd(param);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		CommodityAttrVo vo = CommodityUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(vo);
	}

	public Return<CommodityAttrVo> attrEdit(CommodityAttrParam param) {
		Return<CommodityAttrPo> ret = fCommodity.attrEdit(param);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		CommodityAttrVo vo = CommodityUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(vo);
	}

	public Return<Long> attrDel(long id) {
		Return<Long> ret = fCommodity.attrDel(id);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		long returnId = ret.getData();
		return Constant.createReturn(returnId);
	}

	public Return<List<Long>> attrBatchDel(List<Long> idList) {
		Return<List<Long>> ret = fCommodity.attrBatchDel(idList);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		List<Long> returnListId = ret.getData();
		return Constant.createReturn(returnListId);
	}

}
