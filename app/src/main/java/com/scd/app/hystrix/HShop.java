package com.scd.app.hystrix;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FShop;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HShop implements FShop {

	@Override
	public Return<ShopPo> getShop(long shopId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
