package com.scd.admin.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HCarousel;
import com.scd.joggle.pojo.po.CarouselPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HCarousel.class)
public interface FCarousel {

	@RequestMapping(value = "/carousel/list")
	Return<PageInfo<CarouselPo>> list(@RequestParam(value = "page")int page, @RequestParam(value = "size")int size);

	@RequestMapping(value = "/carousel/add")
	Return<Object> add(@RequestParam(value = "type")int type, @RequestParam(value = "url")String url);

	@RequestMapping(value = "/carousel/edit")
	Return<Object> edit(@RequestParam(value = "id")long id, @RequestParam(value = "type")int type, @RequestParam(value = "url")String url);

	@RequestMapping(value = "/carousel/del")
	Return<Long> del(@RequestParam(value = "id")long id);

	@RequestMapping(value = "/carousel/batchDel")
	Return<List<Long>> batchDel(@RequestBody List<Long> idList);
	
	
}

