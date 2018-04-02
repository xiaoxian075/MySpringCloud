package com.scd.admin.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HCommon;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HCommon.class)
public interface FCommon {

	@RequestMapping(value = "/common/getAreaChild")
	Return<List<AreaShortPojo>> getAreaChild(@RequestParam(value = "parentId")long parentId);

}
