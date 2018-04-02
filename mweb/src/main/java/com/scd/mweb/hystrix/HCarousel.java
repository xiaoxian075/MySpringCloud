package com.scd.mweb.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.joggle.pojo.po.CarouselPo;
import com.scd.mweb.feign.FCarousel;

@Component
public class HCarousel implements FCarousel {

	@Override
	public List<CarouselPo> findByType(int type) {
		return null;
	}

}
