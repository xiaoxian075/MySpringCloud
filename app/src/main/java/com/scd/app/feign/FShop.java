package com.scd.app.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HShop;
import com.scd.joggle.pojo.po.ShopPo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HShop.class)
public interface FShop {
	@RequestMapping(value = "/shop/getShop")
	Return<ShopPo> getShop(@RequestParam(value = "shopId")long shopId);

//	@RequestMapping(value = "/shop/getActivityList")
//	Return<List<ShopActivityPo>> getActivityList(@RequestParam(value = "shopId")long shopId);
}
