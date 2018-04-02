package com.scd.app.pojo.util;

import com.scd.app.pojo.vo.VersionVo;
import com.scd.joggle.pojo.po.VersionPo;

public class VersionUtil {

	public static VersionVo change(VersionPo po) {
		if (po == null) {
			return null;
		}
		
		return new VersionVo(po.getId(), po.getVersion(), po.getState());
	}

}
