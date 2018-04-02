package com.scd.app.hystrix;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FPush;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.PushPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HPush implements FPush {

	@Override
	public Return<PageInfo<PushPo>> list(String account, int page, int size) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<PushPo> getOne(long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<PushPo> readOne(String account, long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
