package com.scd.app.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import com.scd.app.feign.FCommunity;
import com.scd.joggle.pojo.po.CommunityPo;

@Component
public class HCommunity implements FCommunity {

	@Override
	public List<CommunityPo> getPageInfoIncludeState(String account, int type, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommunityPo findOne(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertOne(CommunityPo communityPo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateOne(CommunityPo communityPo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long praise(String account, long id, int type) {
		// TODO Auto-generated method stub
		return 0;
	}



}
