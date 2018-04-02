package com.scd.app.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FAddress;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.AddressPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HAddress implements FAddress {

	@Override
	public Return<List<AddressPo>> getListByAccount(String account) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<AddressPo> add(AddressPo addressPo) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<AddressPo> edit(AddressPo addressPo) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Object> setDefault(long id, int state) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Object> del(long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<AddressPo> getDefault(String account) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}


}
