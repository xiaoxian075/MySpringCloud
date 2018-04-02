package com.scd.admin.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FVersion;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.VersionPo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HVersion implements FVersion {
	@Override
	public Return<List<VersionPo>> getAll() {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<VersionPo> edit(long id, String version, int state) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}
}
