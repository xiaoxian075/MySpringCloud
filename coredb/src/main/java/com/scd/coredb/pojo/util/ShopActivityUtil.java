package com.scd.coredb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.coredb.pojo.db.TShopActivity;
import com.scd.joggle.pojo.po.ShopActivityPo;

public class ShopActivityUtil {
	public static List<ShopActivityPo> change(List<TShopActivity> tList) {
		if (tList == null) {
			return null;
		}
		
		List<ShopActivityPo> poList = new ArrayList<ShopActivityPo>();
		for (TShopActivity tShopActivity : tList) {
			ShopActivityPo po = change(tShopActivity);
			if (po != null) {
				poList.add(po);
			}
		}
		return poList;
	}

	public static ShopActivityPo change(TShopActivity tShopActivity) {
		if (tShopActivity == null) {
			return null;
		}
		
		return new ShopActivityPo(
				tShopActivity.getId(),
				tShopActivity.getShopId(),
				tShopActivity.getTitle(),
				tShopActivity.getCreateTime(),
				tShopActivity.getUpdateTime()
				);
	}
}
