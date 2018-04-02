package com.scd.admin.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HRich;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HRich.class)
public interface FRich {

	@RequestMapping(value = "/rich/selectOne", method = RequestMethod.POST)
	Return<RichBo> selectOne(@RequestParam(value = "type")long type, @RequestParam(value = "foreignId")long foreignId);
	
	@RequestMapping(value = "/rich/submit", method = RequestMethod.POST)
	Return<RichBo> submit(@RequestBody RichBo richBo);
}
