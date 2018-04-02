package com.scd.coredb.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.PushDao;
import com.scd.coredb.pojo.db.TPush;
import com.scd.coredb.pojo.db.TPushState;
import com.scd.coredb.pojo.util.PushUtil;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.po.PushPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class PushService {
	
	@Autowired
	private PushDao pushDao;
	
	@Resource
	private PushStateService pushStateService;

	public Return<PageInfo<PushPo>> list(String account, int page, int size) {
		Order order = new Order(Sort.Direction.DESC, TPush.ID);
		Pageable pageable = new PageRequest(page, size, new Sort(order));
		Page<TPush> pageInfo = pushDao.findAll(pageable);
		
		page = pageInfo.getNumber() + 1;
		long total = pageInfo.getTotalElements();
		List<TPush> kdList = pageInfo.getContent();
		List<PushPo> voList = PushUtil.change(kdList);
		
		for (PushPo po : voList) {
			TPushState tPushState = pushStateService.getOne(account, po.getId());
			if (tPushState != null) {
				po.setState(tPushState.getState());
			} else {
				po.setState(Type.PUSH_READ_NO);
			}
		}
		PageInfo<PushPo> kdPage = new PageInfo<PushPo>(page, total, voList);

		return Constant.createReturn(kdPage);
	}

	public Return<PushPo> getOne(long id) {
		TPush tPush = pushDao.findOne(id);
		if (tPush == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		PushPo po = PushUtil.change(tPush);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(po);
	}

	public Return<PushPo> readOne(String account, long id) {
		TPush tPush = pushDao.findOne(id);
		if (tPush == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		TPushState tPushState = pushStateService.read(account, id);
		if (tPushState == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		PushPo po = PushUtil.change(tPush);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		po.setState(tPushState.getState());
		
		return Constant.createReturn(po);
	}

	public Return<TPush> insertOne(int type, String title, String content) {
		long curTime = System.currentTimeMillis();
		TPush tPush = new TPush(0L, type, title, content, curTime, curTime);
		TPush newTPush = pushDao.save(tPush);
		if (newTPush == null) {
			return Constant.createReturn(ErrorCom.SAVE_ERROR);
		}
		
		return Constant.createReturn(newTPush);
	}
}
