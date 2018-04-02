package com.scd.app.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HVersion;
import com.scd.joggle.pojo.po.VersionPo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HVersion.class)
public interface FVersion {
	@RequestMapping(value = "/version/getVersion")
	Return<VersionPo> getVersion(@RequestParam(value = "type")int type);
}
