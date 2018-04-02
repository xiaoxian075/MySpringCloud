package com.scd.app.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HCarousel;
import com.scd.joggle.pojo.po.CarouselPo;

@FeignClient(value = "${feign.coredb}", fallback = HCarousel.class)
public interface FCarousel {
	
	@RequestMapping(value = "/carousel/findByType")
	List<CarouselPo> findByType(@RequestParam(value = "type") int type);
}
