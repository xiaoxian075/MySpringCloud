package com.scd.admin.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.admin.pojo.vo.Kd100Vo;
import com.scd.joggle.pojo.po.Kd100Po;

public class Kd100Util {

	public static List<Kd100Vo> change(List<Kd100Po> poList) {
		List<Kd100Vo> voList = new ArrayList<Kd100Vo>();
		if (poList == null) {
			return voList;
		}
		
		for (Kd100Po po : poList) {
			Kd100Vo vo = change(po);
			if (vo != null) {
				voList.add(vo);
			}
		}
		
		return voList;
	}

	private static Kd100Vo change(Kd100Po po) {
		if (po == null) {
			return null;
		}
		return new Kd100Vo(po.getId(), po.getName(), po.getCode());
	}

}
