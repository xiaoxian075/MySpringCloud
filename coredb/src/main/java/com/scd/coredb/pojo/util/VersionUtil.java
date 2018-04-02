package com.scd.coredb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.coredb.pojo.db.TVersion;
import com.scd.joggle.pojo.po.VersionPo;

public class VersionUtil {

	public static VersionPo change(TVersion tVersion) {
		if (tVersion == null) {
			return null;
		}
		return new VersionPo(
				tVersion.getId(),
				tVersion.getType(),
				tVersion.getVersion(),
				tVersion.getState(),
				tVersion.getCreateTime(),
				tVersion.getUpdateTime()
				);
	}

	public static List<VersionPo> change(List<TVersion> tList) {
		if (tList == null) {
			return null;
		}
		
		List<VersionPo> poList = new ArrayList<VersionPo>();
		for (TVersion tVersion : tList) {
			VersionPo po = change(tVersion);
			if (po != null) {
				poList.add(po);
			}
		}
		return poList;
	}

}
