package com.scd.app.hystrix;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FVersion;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.VersionPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HVersion implements FVersion {

	@Override
	public Return<VersionPo> getVersion(int state) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
