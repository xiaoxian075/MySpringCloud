package com.scd.app.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FPayRecord;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.PayResultPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HPayRecord implements FPayRecord {

	@Override
	public Return<List<PayResultPo>> getList(int page, int size, String account) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
