package com.scd.mweb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.po.CarouselPo;
import com.scd.mweb.pojo.vo.CarouseShortVo;
import com.scd.mweb.pojo.vo.CarouselVo;

public class CarouseUtil {
	public static CarouselVo change(List<CarouselPo> carouselList) {
		int count = 0;
		List<CarouseShortVo> urlList = new ArrayList<CarouseShortVo>();
		for (CarouselPo po : carouselList) {
			if (po != null) {
				String url = po.getUrl();
				if (url != null && url.length() > 0) {
					urlList.add(new CarouseShortVo(po.getId(), Type.SOWING_COMMUNITY, url));
					count++;
				}
			}
		}
		
		return new CarouselVo(count, urlList);
	}
}
