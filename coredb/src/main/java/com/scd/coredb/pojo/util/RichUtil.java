package com.scd.coredb.pojo.util;

import com.scd.coredb.pojo.db.TRich;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.joggle.pojo.bo.RichLinkBo;
import com.scd.sdk.util.DesUtil;
import com.scd.sdk.util.GsonUtil;

public class RichUtil {

	public static RichBo change(TRich tRich) {
		if (tRich == null) {
			return null;
		}
		
		String data = DesUtil.base64DecodeToStr(tRich.getData());
		String link = tRich.getLink();
		if (data == null || link == null) {
			return null;
		}
		
		RichLinkBo richLinkBo = GsonUtil.toJson(link, RichLinkBo.class);
		if (richLinkBo == null) {
			return null;
		}
		
		return new RichBo(
				tRich.getType(),
				tRich.getForeignId(),
				richLinkBo.getId(),
				data
				);
	}

}
