package com.scd.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FPush;
import com.scd.app.pojo.util.PushUtil;
import com.scd.app.pojo.vo.PushVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.PushPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class PushService {
	
	@Autowired
	private FPush fPush;

	public Return<PageInfo<PushVo>> list(String account, int page, int size) {
		Return<PageInfo<PushPo>> ret = fPush.list(account, page, size);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		PageInfo<PushPo> pageInfo = ret.getData();
		
		List<PushPo> poList = pageInfo.getList();
		List<PushVo> voList = PushUtil.change(poList);
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		PageInfo<PushVo> voPage = new PageInfo<PushVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);
		
		return Constant.createReturn(voPage);
	}

	public Return<PushVo> getOne(long id) {
		Return<PushPo> ret = fPush.getOne(id);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		PushVo vo = PushUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(vo);
	}

	public Return<PushVo> readOne(String account, long id) {
		Return<PushPo> ret = fPush.readOne(account, id);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		PushVo vo = PushUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(vo);
	}

}
