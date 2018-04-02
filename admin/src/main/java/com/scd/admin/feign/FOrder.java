package com.scd.admin.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HOrder;
import com.scd.joggle.pojo.param.OrderSelectParam;
import com.scd.joggle.pojo.po.OrderPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HOrder.class)
public interface FOrder {

	@RequestMapping(value = "/order/getPage", method = RequestMethod.POST)
	Return<PageInfo<OrderPo>> getPage(@RequestBody OrderSelectParam param);

	@RequestMapping(value = "/order/sendGoods", method = RequestMethod.POST)
	Return<Object> sendGoods(@RequestParam(value = "orderOdd")String orderOdd, @RequestParam(value = "code")String code, @RequestParam(value = "expOdd")String expOdd);
}
