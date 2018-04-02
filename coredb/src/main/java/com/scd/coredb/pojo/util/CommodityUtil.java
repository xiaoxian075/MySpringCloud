package com.scd.coredb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.scd.coredb.pojo.db.TCommodity;
import com.scd.coredb.pojo.db.TCommodityAttr;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.param.CommodityParam;
import com.scd.joggle.pojo.po.CommodityAttrPo;
import com.scd.joggle.pojo.po.CommodityOriPo;
import com.scd.joggle.pojo.po.CommodityPo;
import com.scd.sdk.util.GsonUtil;

public class CommodityUtil {

	public static TCommodity change(CommodityParam commodityParam) {
		if (commodityParam == null) {
			return null;
		}
		String serviceItem = commodityParam.getServiceItem();
		if (serviceItem == null) {
			serviceItem = " ";
		}
		String strListPic = GsonUtil.toString(commodityParam.getListPic());
		long curTime = System.currentTimeMillis();
		return new TCommodity(
				commodityParam.getId(),
				commodityParam.getShortTitle(),
				commodityParam.getFullTitle(),
				commodityParam.getShopId(),
				commodityParam.getShopProductType(),
				commodityParam.getShowPic(),
				strListPic,
				commodityParam.getAddrId(),
				"",
				serviceItem,
				0,
				0,
				commodityParam.getIsFreeExp(),
				Type.COMMODITY_STATE_DOWN,
				curTime,
				curTime
				);
	}

	public static CommodityOriPo changeOriPo(TCommodity tCommodity) {
		if (tCommodity == null) {
			return null;
		}
		List<String> listPicture = GsonUtil.toJson(tCommodity.getListPic(), new TypeToken<List<String>>() {}.getType());
		if (listPicture == null) {
			return null;
		}
		
		return new CommodityOriPo(
				tCommodity.getId(), 
				tCommodity.getShortTitle(),
				tCommodity.getFullTitle(),
				tCommodity.getShopId(),
				tCommodity.getShopProductType(),
				"",
				tCommodity.getShowPic(),
				listPicture,
				tCommodity.getAddrId(),
				tCommodity.getAddrName(),
				tCommodity.getServiceItem(),
				tCommodity.getAllSaleNum(),
				tCommodity.getMonthSaleNum(),
				tCommodity.getIsFreeExp(),
				tCommodity.getState(),
				tCommodity.getCreateTime(),
				tCommodity.getUpdateTime());
	}
	
	public static CommodityPo changePo(TCommodity tCommodity) {
		if (tCommodity == null) {
			return null;
		}
		List<String> listPicture = GsonUtil.toJson(tCommodity.getListPic(), new TypeToken<List<String>>() {}.getType());
		if (listPicture == null) {
			return null;
		}
		
		return new CommodityPo(
				tCommodity.getId(), 
				tCommodity.getShortTitle(),
				tCommodity.getFullTitle(),
				tCommodity.getShopId(),
				"",
				tCommodity.getShowPic(),
				listPicture,
				tCommodity.getAddrId(),
				tCommodity.getAddrName(),
				tCommodity.getServiceItem(),
				tCommodity.getAllSaleNum(),
				tCommodity.getMonthSaleNum(),
				tCommodity.getIsFreeExp(),
				tCommodity.getCreateTime(),
				tCommodity.getUpdateTime(),
				null);
	}

	public static List<CommodityAttrPo> change(List<TCommodityAttr> tAttrList) {
		if (tAttrList == null) {
			return null;
		}
		List<CommodityAttrPo> poList = new ArrayList<CommodityAttrPo>();
		for (TCommodityAttr tCommodityAttr : tAttrList) {
			poList.add(new CommodityAttrPo(
					tCommodityAttr.getId(),
					tCommodityAttr.getCommodityId(),
					tCommodityAttr.getAttrId(),
					tCommodityAttr.getAttrName(),
					tCommodityAttr.getPrice(),
					tCommodityAttr.getStockNum(),
					tCommodityAttr.getCreateTime(),
					tCommodityAttr.getUpdateTime()));
		}
		return poList;
	}

	public static CommodityAttrPo changeAttr(TCommodityAttr tCommodityAttr) {
		if (tCommodityAttr == null) {
			return null;
		}
		
		return new CommodityAttrPo(
				tCommodityAttr.getId(),
				tCommodityAttr.getCommodityId(),
				tCommodityAttr.getAttrId(),
				tCommodityAttr.getAttrName(),
				tCommodityAttr.getPrice(),
				tCommodityAttr.getStockNum(),
				tCommodityAttr.getCreateTime(),
				tCommodityAttr.getUpdateTime()
				);
	}

	public static List<CommodityPo> changeList(List<TCommodity> tList) {
		if (tList == null) {
			return null;
		}
		List<CommodityPo> poList = new ArrayList<CommodityPo>();
		for (TCommodity tCommodity : tList) {
			poList.add(changePo(tCommodity));
		}
		return poList;
	}


}
