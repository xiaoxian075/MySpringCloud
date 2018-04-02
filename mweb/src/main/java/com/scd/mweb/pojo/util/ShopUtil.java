package com.scd.mweb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.joggle.pojo.po.ShopActivityPo;
import com.scd.mweb.pojo.vo.IdNameVo;

public class ShopUtil {

	public static List<IdNameVo> change(List<ShopActivityPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<IdNameVo> voList = new ArrayList<IdNameVo>();
		for (ShopActivityPo po : poList) {
			IdNameVo vo = change(po);
			if (vo != null) {
				voList.add(vo);
			}
		}
		return voList;
	}

	public static IdNameVo change(ShopActivityPo po) {
		if (po == null) {
			return null;
		}
		
		return new IdNameVo(po.getId(), po.getTitle());
	}

}
