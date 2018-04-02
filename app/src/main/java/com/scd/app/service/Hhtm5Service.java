package com.scd.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.app.feign.FRich;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.sdk.util.pojo.Return;

@Service
public class Hhtm5Service {
	
	@Autowired
	private FRich fRich;

	public Return<RichBo> getHtml5Info(long type, long id) {
		return fRich.selectOne(type, id);
	}
}
