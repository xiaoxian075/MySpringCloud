package com.scd.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.feign.FRich;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.sdk.util.pojo.Return;

@Service
public class RichService {
	
	@Autowired
	private FRich fRich;

	public Return<RichBo> selectOne(long type, long foreignId) {
		return fRich.selectOne(type, foreignId);
	}

	public Return<RichBo> submit(RichBo richBo) {
		return fRich.submit(richBo);
	}

}
