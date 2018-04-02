package com.scd.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FTrolley;
import com.scd.app.pojo.util.TrolleyUtil;
import com.scd.app.pojo.vo.ShopTrolleyVo;
import com.scd.app.pojo.vo.TrolleyVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.TrolleyPo;
import com.scd.sdk.util.pojo.Return;

@Service
public class TrolleyService {
	@Autowired
	private FTrolley fTrolley;
	
	public Return<List<ShopTrolleyVo>> getList(String account) {
		Return<List<TrolleyPo>> ret = fTrolley.getList(account);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		List<ShopTrolleyVo> voList = TrolleyUtil.change(ret.getData());
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(voList);
	}
	
	public Return<TrolleyVo> add(String odd, String account, long communityId, long attrId, int num) {
		Return<TrolleyPo> ret = fTrolley.add(odd, account, communityId, attrId, num);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		TrolleyVo vo = TrolleyUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(vo);
	}

	public Return<TrolleyVo> editNum(long id, String account, int num) {
		Return<TrolleyPo> ret = fTrolley.editNum(id, account, num);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		TrolleyVo vo = TrolleyUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(vo);
	}
	
	public Return<List<Long>> batchDel(String account, List<Long> idList) {
		return fTrolley.batchDel(account, idList);
	}

}
