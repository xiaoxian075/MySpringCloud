package com.scd.app.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HRich;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HRich.class)
public interface FRich {

	@RequestMapping(value = "/rich/selectOne")
	Return<RichBo> selectOne(@RequestParam(value = "type")long type, @RequestParam(value = "foreignId")long foreignId);

}
