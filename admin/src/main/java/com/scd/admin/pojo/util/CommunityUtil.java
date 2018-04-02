package com.scd.admin.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.admin.pojo.vo.CommunityVo;
import com.scd.joggle.pojo.po.CommunityPo;

public class CommunityUtil {
	public static List<CommunityVo> change(List<CommunityPo> poList) {
		List<CommunityVo> voList = new ArrayList<CommunityVo>();
		if (poList == null) {
			return voList;
		}
		
		for (CommunityPo po : poList) {
			CommunityVo vo = change(po);
			if (vo != null) {
				voList.add(vo);
			}
		}
		
		return voList;
	}

	private static CommunityVo change(CommunityPo po) {
		if (po == null) {
			return null;
		}
		return new CommunityVo(po.getId(), po.getTitle(), po.getType(), po.getUrl(), po.getHitNum(), po.getPraiseNum());
	}
}
