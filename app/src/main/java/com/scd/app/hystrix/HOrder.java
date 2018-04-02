package com.scd.app.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FOrder;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.OrderTypeNumBo;
import com.scd.joggle.pojo.bo.SubmitOrderBo;
import com.scd.joggle.pojo.po.OrderInfoPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HOrder implements FOrder {

	@Override
	public Return<String> submit(SubmitOrderBo submitOrderBo) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

//	@Override
//	public Return<PayPo> getOrderInfo(String odd) {
//		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//	}

	@Override
	public Return<List<OrderTypeNumBo>> selectCount(String account) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<OrderInfoPo>> selectOrderList(int page, int size, String account, int orderState) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<OrderInfoPo> selectOrder(String account, String odd) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<OrderInfoPo> cancelOrder(String account, String odd) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Object> reminderDelivery(String account, String odd) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<OrderInfoPo> confirmationReceipt(String account, String odd) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<OrderInfoPo> dealEvaluatedByOdd(String odd) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<OrderInfoPo> dealEvaluatedById(long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
