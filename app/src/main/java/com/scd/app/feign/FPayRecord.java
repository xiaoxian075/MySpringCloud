package com.scd.app.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HPayRecord;
import com.scd.joggle.pojo.po.PayResultPo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HPayRecord.class)
public interface FPayRecord {

	@RequestMapping(value = "/payRecord/getList")
	Return<List<PayResultPo>> getList(@RequestParam(value = "page")int page, @RequestParam(value = "size")int size, @RequestParam(value = "account")String account);

}
