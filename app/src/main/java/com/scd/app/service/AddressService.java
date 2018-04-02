package com.scd.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.scd.app.constant.Constant;
import com.scd.app.feign.FAddress;
import com.scd.app.pojo.util.AddressUtil;
import com.scd.app.pojo.vo.AddressVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.AddressPo;
import com.scd.sdk.util.pojo.Return;

@Service
public class AddressService {
	
	@Autowired
	private FAddress fAddress;

	public Return<List<AddressVo>> getList(String account) {
		Return<List<AddressPo>> ret = fAddress.getListByAccount(account);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		List<AddressVo> voList = AddressUtil.change(ret.getData());
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}

		return Constant.createReturn(voList);
	}

	public Return<AddressVo> add(String account, String name, String phone, long addrId, String addrName,
			String addrDetail, int isDefault) {
		long curTime = System.currentTimeMillis();
		AddressPo addressPo = new AddressPo(0L, account, name, phone, addrId, addrName, addrDetail, isDefault, curTime, curTime);
				
		Return<AddressPo> ret = fAddress.add(addressPo);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		AddressVo vo = AddressUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}

		return Constant.createReturn(vo);
	}

	public Return<AddressVo> edit(long id, String account, String name, String phone, long addrId, String addrName,
			String addrDetail, int isDefault) {
		long curTime = System.currentTimeMillis();
		AddressPo addressPo = new AddressPo(id, account, name, phone, addrId, addrName, addrDetail, isDefault, curTime, curTime);
				
		Return<AddressPo> ret = fAddress.edit(addressPo);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		AddressVo vo = AddressUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}

		return Constant.createReturn(vo);
	}

	public Return<Object> setDefault(long id, int state) {
		Return<Object> ret = fAddress.setDefault(id, state);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}

		return Constant.createReturn();
	}

	public Return<Object> del(long id) {
		Return<Object> ret = fAddress.del(id);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}

		return Constant.createReturn();
	}

	public Return<AddressVo> getDefault(String account) {				
		Return<AddressPo> ret = fAddress.getDefault(account);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		AddressVo vo = AddressUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}

		return Constant.createReturn(vo);
	}
}
