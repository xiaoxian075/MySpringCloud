package com.scd.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FCommon;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.sdk.util.pojo.Return;

@Service
public class CommonService {
	
	@Autowired
	private FCommon fCommon;

	public Return<List<AreaShortPojo>> getAreaChild(long id) {
		Return<List<AreaShortPojo>> ret = fCommon.getAreaChild(id);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		return ret;
	}

}
