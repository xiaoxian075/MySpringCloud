package com.scd.admin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.CommunityAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.CommunityVo;
import com.scd.admin.service.CommunityService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.IdlistAcceptMsg;
import com.scd.sdk.util.pojo.PageAcceptMsg;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/community")
public class CommunityController {
	
	@Resource
	private CommunityService communityService;

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
		Return<PageInfo<CommunityVo>> ret = communityService.list(page, size);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
	
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request) {
		CommunityAcceptMsg acceptMsg = Constant.subPack(request, CommunityAcceptMsg.class);
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

		String title = acceptMsg.getTitle();
		int type = acceptMsg.getType();
		String url = acceptMsg.getUrl();
		Return<Object> ret = communityService.add(title, type, url);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack();
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request) {
		CommunityAcceptMsg acceptMsg = Constant.subPack(request, CommunityAcceptMsg.class);
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
		String title = acceptMsg.getTitle();
		int type = acceptMsg.getType();
		String url = acceptMsg.getUrl();
		if (id <= 0) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		Return<Object> ret = communityService.edit(id, title, type, url);
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
		Return<Long> ret = communityService.del(id);
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
		Return<List<Long>> ret = communityService.batchDel(idList);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
}

