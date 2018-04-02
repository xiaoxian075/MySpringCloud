package com.scd.mweb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.joggle.pojo.po.CommunityPo;
import com.scd.mweb.pojo.vo.CommunityVo;

public class CommunityUtil {

	public static List<CommunityVo> change(List<CommunityPo> communityList) {
		if (communityList == null) {
			return null;
		}

		List<CommunityVo> voList = new ArrayList<CommunityVo>();
		for (CommunityPo po : communityList) {
			voList.add(
				new CommunityVo(
						po.getId(), 
						po.getTitle(),
						po.getType(),
						po.getUrl(),
						po.getHasPraise(),
						po.getHitNum(),
						po.getPraiseNum()));
		}
		
		return voList;
	}

}
