package com.scd.admin.hystrix;

import org.springframework.stereotype.Component;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FOrder;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.OrderSelectParam;
import com.scd.joggle.pojo.po.OrderPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HOrder implements FOrder {

	@Override
	public Return<PageInfo<OrderPo>> getPage(OrderSelectParam param) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Object> sendGoods(String orderOdd, String code, String expOdd) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
