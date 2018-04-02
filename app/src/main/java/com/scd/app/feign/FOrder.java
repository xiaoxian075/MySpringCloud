package com.scd.app.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HOrder;
import com.scd.joggle.pojo.bo.OrderTypeNumBo;
import com.scd.joggle.pojo.bo.SubmitOrderBo;
import com.scd.joggle.pojo.po.OrderInfoPo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HOrder.class)
public interface FOrder {

	@RequestMapping(value = "/order/submit")
	Return<String> submit(@RequestBody SubmitOrderBo submitOrderBo);

//	@RequestMapping(value = "/order/getOrderInfo")
//	Return<PayPo> getOrderInfo(@RequestParam(value = "odd") String odd);

	@RequestMapping(value = "/order/selectCount")
	Return<List<OrderTypeNumBo>> selectCount(@RequestParam(value = "account")String account);

	@RequestMapping(value = "/order/selectOrderList")
	Return<List<OrderInfoPo>> selectOrderList(@RequestParam(value = "page")int page, @RequestParam(value = "size")int size, @RequestParam(value = "account")String account, @RequestParam(value = "orderState")int orderState);

	@RequestMapping(value = "/order/selectOrder")
	Return<OrderInfoPo> selectOrder(@RequestParam(value = "account")String account, @RequestParam(value = "odd")String odd);

	@RequestMapping(value = "/order/cancelOrder")
	Return<OrderInfoPo> cancelOrder(@RequestParam(value = "account")String account, @RequestParam(value = "odd")String odd);

	@RequestMapping(value = "/order/reminderDelivery")
	Return<Object> reminderDelivery(@RequestParam(value = "account")String account, @RequestParam(value = "odd")String odd);

	@RequestMapping(value = "/order/confirmationReceipt")
	Return<OrderInfoPo> confirmationReceipt(@RequestParam(value = "account") String account, @RequestParam(value = "odd")String odd);

	@RequestMapping(value = "/order/dealEvaluatedByOdd")
	Return<OrderInfoPo> dealEvaluatedByOdd(@RequestParam(value = "odd") String odd);

	@RequestMapping(value = "/order/dealEvaluatedById")
	Return<OrderInfoPo> dealEvaluatedById(@RequestParam(value = "id") long id);
}
