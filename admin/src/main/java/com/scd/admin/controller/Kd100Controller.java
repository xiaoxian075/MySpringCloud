package com.scd.admin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.Kd100AddAcceptMsg;
import com.scd.admin.controller.accept.Kd100EditAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.Kd100Vo;
import com.scd.admin.service.Kd100Service;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.IdlistAcceptMsg;
import com.scd.sdk.util.pojo.PageAcceptMsg;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import com.scd.sdk.util.pojo.ZeroAcceptMsg;

@RestController
@RequestMapping(value = "/kd100")
public class Kd100Controller {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private Kd100Service kd100Service;
	
	@RequestMapping(value = "/getAll", method = RequestMethod.POST)
	public String getAll(HttpServletRequest request) {
		List<Kd100Vo> voList = null;
		try {
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
	
			Return<List<Kd100Vo>> ret = kd100Service.getAll();
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			voList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(voList);
	}
	

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletRequest request) {
		PageAcceptMsg acceptMsg = Constant.subPack(request, PageAcceptMsg.class);
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

		int page = acceptMsg.getPage();
		int size = acceptMsg.getSize();
		Return<PageInfo<Kd100Vo>> ret = kd100Service.list(page, size);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
	
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request) {
		Kd100AddAcceptMsg acceptMsg = Constant.subPack(request, Kd100AddAcceptMsg.class);
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

		String name = acceptMsg.getName();
		String code = acceptMsg.getCode();
		Return<Object> ret = kd100Service.add(name, code);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack();
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request) {
		Kd100EditAcceptMsg acceptMsg = Constant.subPack(request, Kd100EditAcceptMsg.class);
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

		long id = acceptMsg.getId();
		String name = acceptMsg.getName();
		String code = acceptMsg.getCode();
		Return<Object> ret = kd100Service.edit(id, name, code);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack();
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public String del(HttpServletRequest request) {
		IdAcceptMsg acceptMsg = Constant.subPack(request, IdAcceptMsg.class);
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

		long id = acceptMsg.getId();
		Return<Long> ret = kd100Service.del(id);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
	
	@RequestMapping(value = "/batchDel", method = RequestMethod.POST)
	public String batchDel(HttpServletRequest request) {
		IdlistAcceptMsg acceptMsg = Constant.subPack(request, IdlistAcceptMsg.class);
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

		List<Long> idList = acceptMsg.getIdList();
		Return<List<Long>> ret = kd100Service.batchDel(idList);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
}
