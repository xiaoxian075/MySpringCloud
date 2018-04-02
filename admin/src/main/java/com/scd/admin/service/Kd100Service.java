package com.scd.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FKd100;
import com.scd.admin.pojo.util.Kd100Util;
import com.scd.admin.pojo.vo.Kd100Vo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.Kd100Po;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class Kd100Service {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//	private Kd100Dao kd100Dao;
	
	@Autowired
	private FKd100 fKd100;

	public Return<PageInfo<Kd100Vo>> list(int page, int size) {	
		PageInfo<Kd100Vo> kdPage = null;
		try {
			Return<PageInfo<Kd100Po>> ret = fKd100.list(page, size);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			
			PageInfo<Kd100Po> pageInfo = ret.getData();
			List<Kd100Po> poList = pageInfo.getList();
			List<Kd100Vo> voList = Kd100Util.change(poList);
			
			kdPage = new PageInfo<Kd100Vo>(pageInfo.getPage(), pageInfo.getTotal(), voList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return Constant.createReturn(kdPage);
	}

	
	public Return<Long> del(long id) {
		long returnId = 0;
		try {
			Return<Long> ret = fKd100.del(id);
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
			Return<List<Long>> ret = fKd100.batchDel(idList);
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


	public Return<Object> add(String name, String code) {
		try {
			Return<Object> ret = fKd100.add(name, code);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn();
	}


	public Return<Object> edit(long id, String name, String code) {
		try {
			Return<Object> ret = fKd100.edit(id, name, code);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn();
	}


	public Return<List<Kd100Vo>> getAll() {
		Return<List<Kd100Po>> ret = fKd100.getAll();
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		List<Kd100Po> poList = ret.getData();
		List<Kd100Vo> voList = Kd100Util.change(poList);
		return Constant.createReturn(voList);
	}

}
