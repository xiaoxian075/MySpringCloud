package com.scd.app.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.app.pojo.vo.PushVo;
import com.scd.joggle.pojo.po.PushPo;

public class PushUtil {

	public static List<PushVo> change(List<PushPo> poList) {
		if (poList == null) {
			return null;
		}
		List<PushVo> voList = new ArrayList<PushVo>();
		for (PushPo po : poList) {
			PushVo vo = change(po);
			if (vo != null) {
				voList.add(vo);
			}
		}
		return voList;
	}

	public static PushVo change(PushPo po) {
		if (po == null) {
			return null;
		}
		
		return new PushVo(po.getId(), po.getType(), po.getTitle(), po.getContent(), po.getState());
	}

}
