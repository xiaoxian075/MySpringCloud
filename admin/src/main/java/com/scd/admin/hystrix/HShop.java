package com.scd.admin.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FShop;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HShop implements FShop {

	@Override
	public Return<List<ShopPo>> getAllShop() {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<PageInfo<ShopPo>> list(int page, int size) {
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
	public Return<Object> add(String name, List<String> listPic) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Object> edit(long id, String name, List<String> listPic) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}
}
