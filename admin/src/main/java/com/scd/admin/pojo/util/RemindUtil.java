package com.scd.admin.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.admin.pojo.vo.RemindVo;
import com.scd.joggle.pojo.po.RemindPo;

public class RemindUtil {

	public static List<RemindVo> change(List<RemindPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<RemindVo> voList = new ArrayList<RemindVo>();
		for (RemindPo po : poList) {
			RemindVo vo = change(po);
			if (vo != null) {
				voList.add(vo);
			}
		}
		return voList;
	}

	public static RemindVo change(RemindPo po) {
		if (po == null) {
			return null;
		}
		
		return new RemindVo(
				po.getId(),
				po.getAccount(),
				po.getOdd(),
				po.getOrderState(),
				po.getGoodsPrice(),
				po.getExpPrice(),
				po.getAddrInfo(),
				po.getOrderTime(),
				po.getCreateTime(),
				po.getUpdateTime()
				);
	}
}

