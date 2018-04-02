package com.scd.admin.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.admin.pojo.vo.CarouselVo;
import com.scd.joggle.pojo.po.CarouselPo;

public class CarouselUtil {
	public static List<CarouselVo> change(List<CarouselPo> poList) {
		List<CarouselVo> voList = new ArrayList<CarouselVo>();
		if (poList == null) {
			return voList;
		}
		
		for (CarouselPo po : poList) {
			CarouselVo vo = change(po);
			if (vo != null) {
				voList.add(vo);
			}
		}
		
		return voList;
	}

	private static CarouselVo change(CarouselPo po) {
		if (po == null) {
			return null;
		}
		return new CarouselVo(po.getId(), po.getType(), po.getUrl());
	}
}
