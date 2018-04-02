package com.scd.admin.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HRemind;
import com.scd.joggle.pojo.po.RemindPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HRemind.class)
public interface FRemind {

	@RequestMapping(value = "/remind/getList", method = RequestMethod.POST)
	Return<PageInfo<RemindPo>> getList(@RequestParam(value = "page")int page, @RequestParam(value = "size")int size, @RequestParam(value = "state")int state);

	@RequestMapping(value = "/remind/ignore", method = RequestMethod.POST)
	Return<Object> ignore(@RequestParam(value = "id")long id);

}

