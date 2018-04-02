package com.scd.app.hystrix;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FCommon;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.joggle.pojo.po.AreaPo;
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
	public Return<BigDecimal> getExp(long commodityId, long addressId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<List<BigDecimal>> getMulExp(List<List<Long>> commodityIdList, long addressId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
