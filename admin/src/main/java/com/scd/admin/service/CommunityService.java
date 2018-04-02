package com.scd.admin.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FCommunity;
import com.scd.admin.pojo.util.CommunityUtil;
import com.scd.admin.pojo.vo.CommunityVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CommunityPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class CommunityService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FCommunity fCommunity;

	public Return<PageInfo<CommunityVo>> list(int page, int size) {
		PageInfo<CommunityVo> kdPage = null;
		try {
			Return<PageInfo<CommunityPo>> ret = fCommunity.list(page, size);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			
			PageInfo<CommunityPo> pageInfo = ret.getData();
			List<CommunityPo> poList = pageInfo.getList();
			List<CommunityVo> voList = CommunityUtil.change(poList);
			
			kdPage = new PageInfo<CommunityVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return Constant.createReturn(kdPage);
	}


	public Return<Object> add(String title, int type, String url) {
		try {
			Return<Object> ret = fCommunity.add(title, type, url);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn();
	}


	public Return<Object> edit(long id, String title, int type, String url) {
		try {
			Return<Object> ret = fCommunity.edit(id, title, type, url);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn();
	}

	
	public Return<Long> del(long id) {
		long returnId = 0;
		try {
			Return<Long> ret = fCommunity.del(id);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			returnId = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn(returnId);
	}
	
	@Transactional
	public Return<List<Long>> batchDel(List<Long> idList) {
		List<Long> returnIdList = null;
		try {
			Return<List<Long>> ret = fCommunity.batchDel(idList);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			returnIdList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn(returnIdList);
	}


}
