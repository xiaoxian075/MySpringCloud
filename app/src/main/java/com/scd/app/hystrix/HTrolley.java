package com.scd.app.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FTrolley;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.TrolleyPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HTrolley implements FTrolley {

	@Override
	public Return<List<TrolleyPo>> getList(String account) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<TrolleyPo> add(String odd, String account, long communityId, long attrId, int num) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<TrolleyPo> editNum(long id, String account, int num) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<Long>> batchDel(String account, List<Long> idList) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
