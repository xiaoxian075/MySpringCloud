package com.scd.app.pojo.util;

import com.scd.app.pojo.vo.ExpInfoVo;
import com.scd.app.pojo.vo.ShopVo;
import com.scd.joggle.pojo.po.ExpInfoPo;
import com.scd.joggle.pojo.po.ShopPo;

public class CommonUtil {

	public static ExpInfoVo change(ExpInfoPo po) {
		if (po == null) {
			return null;
		}
		
		return new ExpInfoVo(po.getPrice());
	}

	public static ShopVo change(ShopPo po) {
		if (po == null) {
			return null;
		}
		
		
		return new ShopVo(po.getId(), po.getName(), po.getListPic());
	}

}
