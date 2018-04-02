package com.scd.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FVersion;
import com.scd.admin.pojo.util.VersionUtil;
import com.scd.admin.pojo.vo.VersionVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.VersionPo;
import com.scd.sdk.util.pojo.Return;

@Service
public class VersionService {
	
	@Autowired
	private FVersion fVersion;

	public Return<List<VersionVo>> getAll() {
		Return<List<VersionPo>> ret = fVersion.getAll();
		if (Return.isErr(ret)) {
			return Return.createNew(ret.getCode(), ret.getDesc());
		}
		
		List<VersionVo> voList = VersionUtil.change(ret.getData());
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(voList);
	}

	public Return<VersionVo> edit(long id, String version, int state) {
		Return<VersionPo> ret = fVersion.edit(id, version, state);
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
