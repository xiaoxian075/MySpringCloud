package com.scd.admin.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.admin.pojo.vo.ShopVo;
import com.scd.joggle.pojo.po.ShopPo;

public class ShopUtil {

	public static List<ShopVo> change(List<ShopPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<ShopVo> voList = new ArrayList<ShopVo>();
		for (ShopPo po : poList) {
			voList.add(new ShopVo(po.getId(), po.getName(), po.getListPic()));
		}
		return voList;
	}

}
