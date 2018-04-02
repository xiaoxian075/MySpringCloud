package com.scd.app.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.AddressAcceptMsg;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.vo.AddressVo;
import com.scd.app.service.AddressService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.IdStateAcceptMsg;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import com.scd.sdk.util.pojo.ZeroAcceptMsg;

/**
 * @ClassName AddressController
 * @Description 收货地址
 * @author chenjx
 * @date 2018-03-01
 */

@RestController
@RequestMapping(value = "/address")
public class AddressController {

	@Resource
	private AddressService addressService;
	
	/**
	 * 获取地址列表
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public String getList(HttpServletRequest request) {
		ZeroAcceptMsg acceptMsg = Constant.subPack(request, ZeroAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_LOGIN);
		}

		Return<List<AddressVo>> ret = addressService.getList(session.getAccount());
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
	
	/**
	 * 获取默认地址
	 */
	@RequestMapping(value = "/getDefault", method = RequestMethod.POST)
	public String getDefault(HttpServletRequest request) {
		ZeroAcceptMsg acceptMsg = Constant.subPack(request, ZeroAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_LOGIN);
		}

		Return<AddressVo> ret = addressService.getDefault(session.getAccount());
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
	
	/**
	 * 新增地址
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request) {
		AddressAcceptMsg acceptMsg = Constant.subPack(request, AddressAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_LOGIN);
		}

		Return<AddressVo> ret = addressService.add(
				session.getAccount(), 
				acceptMsg.getName(),
				acceptMsg.getPhone(),
				acceptMsg.getAddrId(),
				"",
				acceptMsg.getAddrDetail(),
				acceptMsg.getIsDefault());
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
	
	/**
	 * 编辑地址
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request) {
		AddressAcceptMsg acceptMsg = Constant.subPack(request, AddressAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		if (acceptMsg.getId() == 0) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_LOGIN);
		}

		Return<AddressVo> ret = addressService.edit(
				acceptMsg.getId(),
				session.getAccount(), 
				acceptMsg.getName(),
				acceptMsg.getPhone(),
				acceptMsg.getAddrId(),
				"",
				acceptMsg.getAddrDetail(),
				acceptMsg.getIsDefault());
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}

	/**
	 * 删除地址
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public String del(HttpServletRequest request) {
		IdAcceptMsg acceptMsg =Constant.subPack(request, IdAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		if (acceptMsg.getId() <= 0) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_LOGIN);
		}

		Return<Object> ret = addressService.del(acceptMsg.getId());
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack();
	}
	/**
	 * 设置默认地址
	 * @return
	 */
	@RequestMapping(value = "/setDefault", method = RequestMethod.POST)
	public String setDefault(HttpServletRequest request) {
		IdStateAcceptMsg acceptMsg =Constant.subPack(request, IdStateAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		int state = acceptMsg.getState();
		if (!Type.checkDefaultAddr(state)) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_LOGIN);
		}

		Return<Object> ret = addressService.setDefault(
				acceptMsg.getId(),
				acceptMsg.getState());
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack();
	}
}
