package com.scd.app.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FShopActivity;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopActivityPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HShopActivity implements FShopActivity {

	@Override
	public Return<List<ShopActivityPo>> getActivityList(long shopId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
