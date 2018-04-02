package com.scd.mweb.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.joggle.pojo.po.ShopActivityPo;
import com.scd.mweb.hystrix.HShopActivity;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HShopActivity.class)
public interface FShopActivity {

	@RequestMapping(value = "/shopActivity/getList")
	Return<List<ShopActivityPo>> getActivityList(@RequestParam(value = "shopId")long shopId);
}
