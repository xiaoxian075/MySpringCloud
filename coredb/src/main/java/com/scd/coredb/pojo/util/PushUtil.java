package com.scd.coredb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.coredb.pojo.db.TPush;
import com.scd.joggle.pojo.po.PushPo;

public class PushUtil {

	public static List<PushPo> change(List<TPush> tList) {
		if (tList == null) {
			return null;
		}
		
		List<PushPo> poList = new ArrayList<PushPo>();
		for (TPush tPush : tList) {
			PushPo po = change(tPush);
			if (po != null) {
				poList.add(po);
			}
		}
		return poList;
	}

	public static PushPo change(TPush tPush) {
		if (tPush == null) {
			return null;
		}
		return new PushPo(
				tPush.getId(),
				tPush.getType(),
				tPush.getTitle(),
				tPush.getContent(),
				0,
				tPush.getCreateTime(),
				tPush.getUpdateTime()
				);
	}
}
