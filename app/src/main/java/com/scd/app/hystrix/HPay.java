package com.scd.app.hystrix;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FPay;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.PayInfoPo;
import com.scd.joggle.pojo.po.PayResultPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HPay implements FPay{
	@Override
	public Return<PayInfoPo> requestPay(String account, String orderOdd) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}
	
	@Override
	public Return<PayResultPo> balancePay(String account, String orderOdd, String payPassword) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}
	
	@Override
	public Return<PayInfoPo> recharge(String account, String odd, BigDecimal amount) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<PayResultPo> getPayResult(String account, String tradeOdd) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

//	@Override
//	public Return<PageInfo<PayRecordPo>> getPayList(int page, int size, String account) {
//		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//	}

	@Override
	public boolean yunpayNotice(String data) {
		return false;
	}
//	@Override
//	public Return<Object> payOrder(OrderPayPo orderPayPo) {
//		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//	}
//
//	@Override
//	public Return<Object> yunpayNotice(String param) {
//		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//	}
//
//	@Override
//	public Return<Object> recharge(RechargePo rechargePo) {
//		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//	}
//
//	@Override
//	public Return<Object> payUpgradeVip(UpgradeVipPo upgradeVipPo) {
//		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//	}
//
//	@Override
//	public Return<PageInfo<OrderPayPo>> getPayList(String account, int page, int size) {
//		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//	}
//
//	@Override
//	public Return<OrderPayPo> getPayInfoByOrderId(String orderId) {
//		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//	}
//
//	@Override
//	public Return<OrderPayPo> getPayInfoByOdd(String odd) {
//		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//	}

	
}
