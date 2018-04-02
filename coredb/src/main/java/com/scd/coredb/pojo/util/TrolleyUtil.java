package com.scd.coredb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.coredb.pojo.db.TTrolley;
import com.scd.joggle.pojo.po.TrolleyPo;

public class TrolleyUtil {

	public static List<TrolleyPo> change(List<TTrolley> tList) {
		if (tList == null) {
			return null;
		}
		
		List<TrolleyPo> poList = new ArrayList<TrolleyPo>();
		for (TTrolley tTrolley : tList) {
			TrolleyPo po = change(tTrolley);
			if (po == null) {
				continue;
			}
			poList.add(po);
		}
		return poList;
	}

	public static TrolleyPo change(TTrolley tTrolley) {
		if (tTrolley == null) {
			return null;
		}
		
		return new TrolleyPo(
				tTrolley.getId(),
				tTrolley.getOdd(),
				tTrolley.getAccount(),
				tTrolley.getCommudityId(),
				tTrolley.getTitle(),
				tTrolley.getShowPic(),
				tTrolley.getAttrId(),
				tTrolley.getAttrName(),
				tTrolley.getShopId(),
				tTrolley.getShopName(),
				tTrolley.getPrice(),
				tTrolley.getNum(),
				tTrolley.getCreateTime(),
				tTrolley.getUpdateTime()
				);
	}
}
