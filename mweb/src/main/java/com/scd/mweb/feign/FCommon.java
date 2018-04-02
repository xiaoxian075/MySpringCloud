package com.scd.mweb.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.mweb.hystrix.HCommon;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.joggle.pojo.po.AreaPo;
import com.scd.joggle.pojo.po.ExpInfoPo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HCommon.class)
public interface FCommon {
	
	@RequestMapping(value = "/common/getAreaChild")
	Return<List<AreaShortPojo>> getAreaChild(@RequestParam(value = "parentId")long parentId);

	@RequestMapping(value = "/common/getAllArea")
	Return<List<AreaPo>> getAllArea();

	@RequestMapping(value = "/common/getExp")
	Return<ExpInfoPo> getExp(@RequestParam(value = "commodityId")long commodityId, @RequestParam(value = "addressId")long addressId);
}
