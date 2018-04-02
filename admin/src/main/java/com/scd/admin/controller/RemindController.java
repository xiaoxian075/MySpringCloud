package com.scd.admin.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.RemindPageAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.RemindVo;
import com.scd.admin.service.RemindService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/remind")
public class RemindController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RemindService remindService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletRequest request) {
		PageInfo<RemindVo> pageInfo = null;
		try {
			RemindPageAcceptMsg acceptMsg = Constant.subPack(request, RemindPageAcceptMsg.class);
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
		      
			Return<PageInfo<RemindVo>> ret = remindService.list(acceptMsg.getPage(), acceptMsg.getSize(), acceptMsg.getState());
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
	
	@RequestMapping(value = "/ignore", method = RequestMethod.POST)
    public String ignore(HttpServletRequest request) {
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
		          return Constant.pack(ErrorCom.GET_LOGIN);
		      }
	
	        long id = acceptMsg.getId();
	        Return<Object> ret = remindService.ignore(id);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack();
    }
}
