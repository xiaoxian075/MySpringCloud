package com.scd.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FCommunity;
import com.scd.app.pojo.util.CommunityUtil;
import com.scd.app.pojo.vo.CommunityVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CommunityPo;
import com.scd.sdk.util.pojo.Return;


@Service
public class CommunityService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	
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

	public Return<Long> hit(long id) {
		long newHit = 0L;
		try {
			CommunityPo communityPo = fCommunity.findOne(id);
			if (communityPo == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}
			
			newHit = communityPo.getHitNum() + 1;
			communityPo.setHitNum(newHit);
			communityPo.setUpdateTime(System.currentTimeMillis());
			if (!fCommunity.insertOne(communityPo)) {
				return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
			}
		} catch (Exception e) {
			newHit = 0L;
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR); 
		}
		
		return Constant.createReturn(newHit);
	}

	public Return<Long> praise(String account, long id, int type) {
		long newPraiseNum = 0L;
		try {
			CommunityPo communityPo = fCommunity.findOne(id);
			if (communityPo == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}
			newPraiseNum = fCommunity.praise(account, id, type);
			if (newPraiseNum < 0) {
				return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
			}
		} catch (Exception e) {
			newPraiseNum = 0L;
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR); 
		}
		
		return Constant.createReturn(newPraiseNum);
	}
	
	

}
