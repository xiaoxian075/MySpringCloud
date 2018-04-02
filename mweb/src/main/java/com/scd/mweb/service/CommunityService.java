package com.scd.mweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CommunityPo;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.feign.FCommunity;
import com.scd.mweb.pojo.util.CommunityUtil;
import com.scd.mweb.pojo.vo.CommunityVo;
import com.scd.sdk.util.pojo.Return;


@Service
public class CommunityService {

	@Autowired
	private FCommunity fCommunity;
	
	public Return<List<CommunityVo>> getList(String account, int type, int page, int size) {
		List<CommunityPo> communityList = fCommunity.getPageInfoIncludeState(account, type, page, size);
		if (communityList == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		
		List<CommunityVo> voList = CommunityUtil.change(communityList);
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}

		return Constant.createReturn(voList);
	}
}
