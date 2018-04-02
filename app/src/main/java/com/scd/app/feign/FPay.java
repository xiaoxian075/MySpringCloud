package com.scd.app.feign;

import com.scd.app.hystrix.HPay;
import com.scd.joggle.pojo.po.PayInfoPo;
import com.scd.joggle.pojo.po.PayResultPo;
import com.scd.sdk.util.pojo.Return;

import java.math.BigDecimal;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "${feign.coredb}", fallback = HPay.class)
public interface FPay {
	
    @RequestMapping(value = "/pay/requestPay")
	Return<PayInfoPo> requestPay(@RequestParam(value = "account")String account, @RequestParam(value = "orderOdd")String orderOdd);
    
    @RequestMapping(value = "/pay/balancePay")
	Return<PayResultPo> balancePay(@RequestParam(value = "account")String account, @RequestParam(value = "orderOdd")String orderOdd, @RequestParam(value = "payPassword")String payPassword);

    @RequestMapping(value = "/pay/recharge")
	Return<PayInfoPo> recharge(@RequestParam(value = "account")String account, @RequestParam(value = "odd")String odd, @RequestParam(value = "amount")BigDecimal amount);
    
    @RequestMapping(value = "/pay/getPayResult")
	Return<PayResultPo> getPayResult(@RequestParam(value = "account")String account, @RequestParam(value = "tradeOdd")String tradeOdd);

//    @RequestMapping(value = "/pay/getPayList")
//	Return<PageInfo<PayRecordPo>> getPayList(@RequestParam(value = "page")int page, @RequestParam(value = "size")int size, @RequestParam(value = "account")String account);

    @RequestMapping(value = "/pay/yunpayNotice")
	boolean yunpayNotice(@RequestParam(value = "data") String data);
}
