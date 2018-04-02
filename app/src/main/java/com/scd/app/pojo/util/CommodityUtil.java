package com.scd.app.pojo.util;

import com.scd.app.pojo.vo.*;
import com.scd.joggle.pojo.po.CommodityAttrPo;
import com.scd.joggle.pojo.po.CommodityPo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CommodityUtil {

	public static List<CommodityVo> change(List<CommodityPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<CommodityVo> voList = new ArrayList<CommodityVo>();
		for (CommodityPo po : poList) {
			CommodityVo vo = change(po);
			if (vo == null) {
				return null;
			}
			voList.add(vo);
		}
		return voList;
	}

	public static CommodityVo change(CommodityPo po) {
		if (po == null) {
			return null;
		}
		List<CommodityAttrPo> attrList = po.getAttrList();
		if (attrList == null) {
			return null;
		}

		long allStockNum = 0;
		List<CommodityAttrVo> attrVoList = new ArrayList<CommodityAttrVo>();
		for (CommodityAttrPo attrPo : attrList) {
			CommodityAttrVo attrVo = new CommodityAttrVo(
					attrPo.getId(),
					attrPo.getCommodityId(),
					attrPo.getAttrId(),
					attrPo.getAttrName(),
					attrPo.getPrice(),
					attrPo.getStockNum());
			allStockNum += attrPo.getStockNum();
			attrVoList.add(attrVo);
		}
	
		return new CommodityVo(
				po.getId(),
				po.getShortTitle(),
				po.getFullTitle(),
				po.getShopId(),
				po.getShowPic(),
				po.getListPic(),
				po.getAddrId(),
				po.getAddrName(),
				po.getServiceItem(),
				po.getAllSaleNum(),
				po.getMonthSaleNum(),
				allStockNum,
				po.getIsFreeExp(),
				attrVoList);
	}

	public static List<CommodityShortVo> changeShort(List<CommodityVo> voList) {
		if (voList == null) {
			return null;
		}
		List<CommodityShortVo> shortList = new ArrayList<CommodityShortVo>();
		for (CommodityVo vo : voList) {
			List<CommodityAttrVo> attrList = vo.getAttrList();
			if (attrList == null || attrList.size() == 0) {
				continue;
			}
			CommodityAttrVo attrVo = attrList.get(0);
			BigDecimal price = attrVo.getPrice();
			shortList.add(new CommodityShortVo(vo.getId(), vo.getShortTitle(), vo.getShowPic(), vo.getAllSaleNum(), price));
		}

		return shortList;
	}

	public static CommodityDetailVo changeDetail(CommodityVo vo) {
		if (vo == null) {
			return null;
		}
		
		List<CommodityAttrVo> attrList = vo.getAttrList();
		if (attrList == null) {
			return null;
		}
		CommodityAttrVo attrVo = attrList.get(0);
		BigDecimal price = attrVo.getPrice();
		CommodityDetailVo detailVo = new CommodityDetailVo(
				vo.getId(), 
				vo.getFullTitle(),
				vo.getShopId(),
				vo.getListPic(),
				vo.getAddrName(),
				vo.getServiceItem(),
				vo.getMonthSaleNum(),
				vo.getIsFreeExp(),
				attrVo.getAttrId(),
				attrVo.getAttrName(),
				price);
		

		return detailVo;
	}

	public static CommodityAttrShowVo changeAttr(CommodityVo vo) {
		if (vo == null) {
			return null;
		}
		List<CommodityAttrVo> attrList = vo.getAttrList();
		if (attrList == null || attrList.size() == 0) {
			return null;
		}
		
		CommodityAttrVo attrVo = attrList.get(0);
		BigDecimal price = attrVo.getPrice();
		long stockNum = attrVo.getStockNum();

		return new CommodityAttrShowVo(
				vo.getId(),
				vo.getShowPic(),
				price,
				stockNum,
				attrList);
	}

	public static List<CommodityAssortVo> changeAssort(List<CommodityPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<CommodityAssortVo> voList = new ArrayList<CommodityAssortVo>();
		for (CommodityPo po : poList) {
			List<CommodityAttrPo> attrPoList = po.getAttrList();
			if (attrPoList == null || attrPoList.size() == 0) {
				continue;
			}
			CommodityAttrPo attrPo = attrPoList.get(0);
			voList.add(new CommodityAssortVo(
					po.getId(),
					po.getShowPic(),
					attrPo.getPrice()
					));
		}
		
		return voList;
	}

	public static List<CommodityRecommendVo> changeRecommend(List<CommodityPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<CommodityRecommendVo> voList = new ArrayList<CommodityRecommendVo>();
		for (CommodityPo po : poList) {
			List<CommodityAttrPo> attrPoList = po.getAttrList();
			if (attrPoList.size() == 0) {
				continue;
			}
			CommodityAttrPo attrPo = attrPoList.get(0);
			voList.add(new CommodityRecommendVo(
					po.getId(),
					po.getShortTitle(),
					po.getShowPic(),
					po.getAllSaleNum(),
					attrPo.getPrice()
					));
		}
		
		return voList;
	}

}
