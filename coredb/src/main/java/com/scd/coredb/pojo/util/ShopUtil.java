package com.scd.coredb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.scd.coredb.pojo.db.TShop;
import com.scd.joggle.pojo.po.ShopPo;
import com.scd.sdk.util.GsonUtil;

public class ShopUtil {
	public static List<ShopPo> change(List<TShop> shopList) {
		if (shopList == null) {
			return null;
		}
		
		List<ShopPo> poList = new ArrayList<ShopPo>();
		for (TShop tShop : shopList) {
			String strListPic = tShop.getListPic();
			List<String> listPic = GsonUtil.toJson(strListPic, new TypeToken<List<String>>() {}.getType());
			if (listPic == null) {
				continue;
			}
			poList.add(new ShopPo(tShop.getId(), tShop.getName(), listPic, tShop.getCreateTime(), tShop.getUpdateTime()));
		}
		return poList;
	}
	
	public static ShopPo change(TShop tShop) {
		if (tShop == null) {
			return null;
		}
		
		String strListPic = tShop.getListPic();
		List<String> listPic = GsonUtil.toJson(strListPic, new TypeToken<List<String>>() {}.getType());
		if (listPic == null) {
			return null;
		}
		
		return new ShopPo(tShop.getId(), tShop.getName(), listPic, tShop.getCreateTime(), tShop.getUpdateTime());
	}


}
