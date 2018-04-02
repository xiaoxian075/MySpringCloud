package com.scd.coredb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.coredb.pojo.db.TRemind;
import com.scd.joggle.pojo.po.RemindPo;

public class RemindUtil {

	public static List<RemindPo> change(List<TRemind> tList) {
		if (tList == null) {
			return null;
		}
		
		List<RemindPo> poList = new ArrayList<RemindPo>();
		for (TRemind tRemind : tList) {
			RemindPo po = change(tRemind);
			if (po != null) {
				poList.add(po);
			}
		}
		return poList;
	}

	public static RemindPo change(TRemind tRemind) {
		if (tRemind == null) {
			return null;
		}
		
		return new RemindPo(
				tRemind.getId(),
				tRemind.getAccount(),
				tRemind.getOdd(),
				tRemind.getOrderState(),
				tRemind.getGoodsPrice(),
				tRemind.getExpPrice(),
				tRemind.getAddrInfo(),
				tRemind.getOrderTime(),
				tRemind.getState(),
				tRemind.getCreateTime(),
				tRemind.getUpdateTime()
				);
	}
}

