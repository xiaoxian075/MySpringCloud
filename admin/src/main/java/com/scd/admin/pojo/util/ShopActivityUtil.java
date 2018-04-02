package com.scd.admin.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.admin.pojo.vo.ShopActivityVo;
import com.scd.joggle.pojo.po.ShopActivityPo;

public class ShopActivityUtil {

	public static List<ShopActivityVo> change(List<ShopActivityPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<ShopActivityVo> voList = new ArrayList<ShopActivityVo>();
		for (ShopActivityPo po : poList) {
			ShopActivityVo vo = change(po);
			if (vo != null) {
				voList.add(vo);
			}
		}
		return voList;
	}

	public static ShopActivityVo change(ShopActivityPo po) {
		if (po == null) {
			return null;
		}
		
		return new ShopActivityVo(
				po.getId(),
				po.getShopId(),
				po.getTitle(),
				po.getCreateTime(),
				po.getUpdateTime()
				);
	}

}
