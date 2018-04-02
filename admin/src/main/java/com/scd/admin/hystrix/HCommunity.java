package com.scd.admin.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FCommunity;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CommunityPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HCommunity implements FCommunity {

	@Override
	public Return<PageInfo<CommunityPo>> list(int page, int size) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Object> add(String title, int type, String url) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Object> edit(long id, String title, int type, String url) {
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


}
