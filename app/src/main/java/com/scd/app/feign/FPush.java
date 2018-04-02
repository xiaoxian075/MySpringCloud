package com.scd.app.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HPush;
import com.scd.joggle.pojo.po.PushPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HPush.class)
public interface FPush {
	
	@RequestMapping(value = "/push/list")
	Return<PageInfo<PushPo>> list(@RequestParam(value = "account")String account, @RequestParam(value = "page")int page, @RequestParam(value = "size")int size);
	
	@RequestMapping(value = "/push/getOne")
	Return<PushPo> getOne(@RequestParam(value = "id")long id);
	
	@RequestMapping(value = "/push/readOne")
	Return<PushPo> readOne(@RequestParam(value = "account")String account, @RequestParam(value = "id")long id);
	
}
