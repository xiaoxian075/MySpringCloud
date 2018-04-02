package com.scd.admin.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FCommon;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HCommon implements FCommon {

	@Override
	public Return<List<AreaShortPojo>> getAreaChild(long parentId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
