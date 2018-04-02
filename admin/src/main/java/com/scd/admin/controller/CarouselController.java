package com.scd.admin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.CarouselAddAcceptMsg;
import com.scd.admin.controller.accept.CarouselEditAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.CarouselVo;
import com.scd.admin.service.CarouselService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.IdlistAcceptMsg;
import com.scd.sdk.util.pojo.PageAcceptMsg;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/carousel")
public class CarouselController {
	
	@Resource
	private CarouselService carouselService;

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
		Return<PageInfo<CarouselVo>> ret = carouselService.list(page, size);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
	
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request) {
		CarouselAddAcceptMsg acceptMsg = Constant.subPack(request, CarouselAddAcceptMsg.class);
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

		int type = acceptMsg.getType();
		String url = acceptMsg.getUrl();
		Return<Object> ret = carouselService.add(type, url);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack();
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request) {
		CarouselEditAcceptMsg acceptMsg = Constant.subPack(request, CarouselEditAcceptMsg.class);
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
		int type = acceptMsg.getType();
		String url = acceptMsg.getUrl();
		Return<Object> ret = carouselService.edit(id, type, url);
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
		Return<Long> ret = carouselService.del(id);
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
		Return<List<Long>> ret = carouselService.batchDel(idList);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
}

