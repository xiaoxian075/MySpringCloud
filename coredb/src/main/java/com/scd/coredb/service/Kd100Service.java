package com.scd.coredb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.Kd100Dao;
import com.scd.coredb.pojo.db.TKd100;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.Kd100Po;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class Kd100Service {

	@Autowired
	private Kd100Dao kd100Dao;
	
	public Return<PageInfo<Kd100Po>> list(int page, int size) {

		Sort.Order order = new Sort.Order(Sort.Direction.DESC, TKd100.ID);
		Pageable pageable = new PageRequest(page, size, new Sort(order));
		Page<TKd100> pageInfo = kd100Dao.findAll(pageable);
		
		page = pageInfo.getNumber() + 1;
		long total = pageInfo.getTotalElements();
		List<TKd100> kdList = pageInfo.getContent();
		List<Kd100Po> voList = TKd100.change(kdList);
		
		PageInfo<Kd100Po> kdPage = new PageInfo<Kd100Po>(page, total, voList);

		
		return Constant.createReturn(kdPage);
	}

	public Return<Long> del(long id) {
		kd100Dao.delete(id);
		return Constant.createReturn(id);
	}

	@Transactional
	public Return<List<Long>> batchDel(List<Long> idList) {
		for (long id : idList) {
			kd100Dao.delete(id);
		}
		return Constant.createReturn(idList);
	}

	public Return<Object> add(String name, String code) {
		TKd100 tKd100 = kd100Dao.findByName(name);
		if (tKd100 != null) {
			return Constant.createReturn(ErrorCom.KD100_NAME_EXIST);
		}
		
		tKd100 = kd100Dao.findByCode(code);
		if (tKd100 != null) {
			return Constant.createReturn(ErrorCom.KD100_CODE_EXIST);
		}
		
		long curTime = System.currentTimeMillis();
		tKd100 = new TKd100(0, name, code, curTime, curTime);
		kd100Dao.save(tKd100);

		return Constant.createReturn();
	}


	@RequestMapping(value = "/edit")
	public Return<Object> edit(long id, String name, String code) {

		TKd100 tKd100 = kd100Dao.findOne(id);
		if (tKd100 == null) {
			return Constant.createReturn(ErrorCom.KD100_NOT_EXIST);
		}
		
		boolean isUpdate = false;
		if (!name.equals(tKd100.getName())) {
			TKd100 tmpNode = kd100Dao.findByName(name);
			if (tmpNode != null) {
				return Constant.createReturn(ErrorCom.KD100_NAME_EXIST);
			}
			tKd100.setName(name);
			isUpdate = true;
		}
		if (!code.equals(tKd100.getCode())) {
			TKd100 tmpNode = kd100Dao.findByCode(code);
			if (tmpNode != null) {
				return Constant.createReturn(ErrorCom.KD100_NAME_EXIST);
			}
			tKd100.setCode(code);
			isUpdate = true;
		}
		
		if (!isUpdate) {
			return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
		}
		
		tKd100.setUpdateTime(System.currentTimeMillis());
		kd100Dao.save(tKd100);

		return Constant.createReturn();
	}

	public Return<List<Kd100Po>> getAll() {
		Iterable<TKd100> iter = kd100Dao.findAll();
		if (iter == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		List<TKd100> tList = Lists.newArrayList(iter);
		List<Kd100Po> voList = TKd100.change(tList);
		return Constant.createReturn(voList);
	}
}
