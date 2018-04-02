package com.scd.admin.hystrix;

import org.springframework.stereotype.Component;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FRemind;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.RemindPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HRemind implements FRemind {

	@Override
	public Return<PageInfo<RemindPo>> getList(int page, int size, int state) {
		return Constant.createReturn(ErrorCom.ERROR);
	}

	@Override
	public Return<Object> ignore(long id) {
		return Constant.createReturn(ErrorCom.ERROR);
	}

}
