package com.scd.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FShopActivity;
import com.scd.app.pojo.util.ShopUtil;
import com.scd.app.pojo.vo.IdNameVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopActivityPo;
import com.scd.sdk.util.pojo.Return;

@Service
public class ShopService {
	
	@Autowired
	private FShopActivity fShopActivity;

	public Return<List<IdNameVo>> getActivityList(long shopId) {
		Return<List<ShopActivityPo>> ret = fShopActivity.getActivityList(shopId);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		List<IdNameVo> voList = ShopUtil.change(ret.getData());
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(voList);
	}

}
