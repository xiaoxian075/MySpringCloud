package com.scd.mweb.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.mweb.constant.Constant;
import com.scd.mweb.feign.FCommon;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.joggle.pojo.po.AreaPo;
import com.scd.joggle.pojo.po.ExpInfoPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HCommon implements FCommon {

	@Override
	public Return<List<AreaShortPojo>> getAreaChild(long parentId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<AreaPo>> getAllArea() {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<ExpInfoPo> getExp(long commodityId, long addressId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
