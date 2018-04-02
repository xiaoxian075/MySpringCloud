package com.scd.mweb.hystrix;

import org.springframework.stereotype.Component;

import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopPo;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.feign.FShop;
import com.scd.sdk.util.pojo.Return;

@Component
public class HShop implements FShop {

	@Override
	public Return<ShopPo> getShop(long shopId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

//	@Override
//	public Return<List<ShopActivityPo>> getActivityList(long shopId) {
//		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//	}

}
