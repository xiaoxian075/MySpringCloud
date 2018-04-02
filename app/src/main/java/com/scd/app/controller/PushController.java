package com.scd.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.app.constant.Constant;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.vo.PushVo;
import com.scd.app.service.PushService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.PageAcceptMsg;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/push")
public class PushController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private PushService pushService;
	

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String getList(HttpServletRequest request) {
		PageInfo<PushVo> pageInfo = null;
		try {
			PageAcceptMsg acceptMsg = Constant.subPack(request, PageAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_SESSION);
			}
			
			int page = acceptMsg.getPage();
			int size = acceptMsg.getSize();
			Return<PageInfo<PushVo>> ret = pushService.list(session.getAccount(), page, size);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			pageInfo = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(pageInfo);
	}
	
	@RequestMapping(value = "/getOne", method = RequestMethod.POST)
	public String getOne(HttpServletRequest request) {
		PushVo vo = null;
		try {
			IdAcceptMsg acceptMsg = Constant.subPack(request, IdAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_SESSION);
			}
			
			Return<PushVo> ret = pushService.getOne(acceptMsg.getId());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			vo = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(vo);
	}
	
	@RequestMapping(value = "/readOne", method = RequestMethod.POST)
	public String readOne(HttpServletRequest request) {
		PushVo vo = null;
		try {
			IdAcceptMsg acceptMsg = Constant.subPack(request, IdAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_SESSION);
			}
			
			Return<PushVo> ret = pushService.readOne(session.getAccount(), acceptMsg.getId());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			vo = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(vo);
	}
}
