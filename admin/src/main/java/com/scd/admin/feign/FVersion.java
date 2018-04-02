package com.scd.admin.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HVersion;
import com.scd.joggle.pojo.po.VersionPo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HVersion.class)
public interface FVersion {

	@RequestMapping(value = "/version/getAll", method = RequestMethod.POST)
	Return<List<VersionPo>> getAll();

	@RequestMapping(value = "/version/edit", method = RequestMethod.POST)
	Return<VersionPo> edit(@RequestParam(value = "id")long id, @RequestParam(value = "version")String version, @RequestParam(value = "state")int state);

}
