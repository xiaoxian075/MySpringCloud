package com.scd.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FVersion;
import com.scd.app.pojo.util.VersionUtil;
import com.scd.app.pojo.vo.VersionVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.VersionPo;
import com.scd.sdk.util.pojo.Return;

@Service
public class VersionService {
	
	@Autowired
	private FVersion fVersion;

	public Return<VersionVo> getVersion(int type) {
		Return<VersionPo> ret = fVersion.getVersion(type);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		VersionVo vo = VersionUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(vo);
	}
}
