package com.scd.admin.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FKd100;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.Kd100Po;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HKd100 implements FKd100 {

	@Override
	public Return<PageInfo<Kd100Po>> list(int page, int size) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Long> del(long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<Long>> batchDel(List<Long> idList) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Object> add(String name, String code) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Object> edit(long id, String name, String code) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<Kd100Po>> getAll() {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
