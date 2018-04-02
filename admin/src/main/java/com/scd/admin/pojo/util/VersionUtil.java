package com.scd.admin.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.admin.pojo.vo.VersionVo;
import com.scd.joggle.pojo.po.VersionPo;

public class VersionUtil {

	public static List<VersionVo> change(List<VersionPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<VersionVo> voList = new ArrayList<VersionVo>();
		for (VersionPo po : poList) {
			VersionVo vo = change(po);
			if (vo != null) {
				voList.add(vo);
			}
		}
		return voList;
	}

	public static VersionVo change(VersionPo po) {
		if (po == null) {
			return null;
		}
		
		return new VersionVo(
				po.getId(),
				po.getType(),
				po.getVersion(),
				po.getState(),
				po.getCreateTime(),
				po.getUpdateTime()
				);
	}
}
