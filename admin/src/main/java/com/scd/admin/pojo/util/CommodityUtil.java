package com.scd.admin.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.admin.controller.accept.CommodityAcceptMsg;
import com.scd.admin.controller.accept.CommodityAttrAcceptMsg;
import com.scd.admin.pojo.vo.CommodityAttrVo;
import com.scd.admin.pojo.vo.CommodityVo;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.param.CommodityAttrParam;
import com.scd.joggle.pojo.param.CommodityParam;
import com.scd.joggle.pojo.po.CommodityAttrPo;
import com.scd.joggle.pojo.po.CommodityOriPo;

public class CommodityUtil {

	public static List<CommodityVo> change(List<CommodityOriPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<CommodityVo> voList = new ArrayList<CommodityVo>();
		for (CommodityOriPo po : poList) {
			CommodityVo vo = change(po);
			if (vo == null) {
				return null;
			}
			voList.add(vo);
		}
		return voList;
	}
	
	/**
	 * 有运费的
	 */
	public static CommodityParam change(CommodityAcceptMsg acceptMsg) {
		if (acceptMsg == null) {
			return null;
		}
		
		return new CommodityParam(
				acceptMsg.getId(),
				acceptMsg.getShortTitle(),
				acceptMsg.getFullTitle(),
				acceptMsg.getShopId(),
				acceptMsg.getShopProductType(),
				acceptMsg.getShowPic(),
				acceptMsg.getListPic(),
				acceptMsg.getAddrId(),
				acceptMsg.getServiceItem(),
				acceptMsg.getIsFreeExp(),
				Type.COMMODITY_EXP_ISFREE_NO);
	}

	public static CommodityVo change(CommodityOriPo po) {
		if (po == null) {
			return null;
		}
		
		return new CommodityVo(
				po.getId(),
				po.getShortTitle(),
				po.getFullTitle(),
				po.getShopId(),
				po.getShopProductType(),
				po.getShopName(),
				po.getShowPic(),
				po.getListPic(),
				po.getAddrId(),
				po.getAddrName(),
				po.getServiceItem(),
				po.getAllSaleNum(),
				po.getMonthSaleNum(),
				po.getIsFreeExp(),
				po.getState(),
				po.getCreateTime(),
				po.getUpdateTime());
	}

	public static List<CommodityAttrVo> changeAttr(List<CommodityAttrPo> attrPoList) {
		if (attrPoList == null) {
			return null;
		}
		
		List<CommodityAttrVo> voList = new ArrayList<CommodityAttrVo>();
		for (CommodityAttrPo po : attrPoList) {
			voList.add(new CommodityAttrVo(
					po.getId(),
					po.getCommodityId(),
					po.getAttrId(),
					po.getAttrName(),
					po.getPrice(),
					po.getStockNum(),
					po.getCreateTime(),
					po.getUpdateTime()));
		}
		return voList;
	}

	public static CommodityAttrParam change(CommodityAttrAcceptMsg acceptMsg) {
		if (acceptMsg == null) {
			return null;
		}
		
		return new CommodityAttrParam(
				acceptMsg.getId(),
				acceptMsg.getCommodityId(),
				acceptMsg.getAttrId(),
				acceptMsg.getAttrName(),
				acceptMsg.getPrice(),
				acceptMsg.getStockNum()
				);
	}

	public static CommodityAttrVo change(CommodityAttrPo po) {
		if (po == null) {
			return null;
		}
		return new CommodityAttrVo(
				po.getId(),
				po.getCommodityId(),
				po.getAttrId(),
				po.getAttrName(),
				po.getPrice(),
				po.getStockNum(),
				po.getCreateTime(),
				po.getUpdateTime());
	}
	

}
