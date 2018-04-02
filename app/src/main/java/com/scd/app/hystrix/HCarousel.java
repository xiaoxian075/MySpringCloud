package com.scd.app.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.app.feign.FCarousel;
import com.scd.joggle.pojo.po.CarouselPo;

@Component
public class HCarousel implements FCarousel {

	@Override
	public List<CarouselPo> findByType(int type) {
		return null;
	}



}
