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
import com.scd.admin.controller.accept.VersionEditAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.VersionVo;
import com.scd.admin.service.VersionService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import com.scd.sdk.util.pojo.ZeroAcceptMsg;

@RestController
@RequestMapping(value = "/version")
public class VersionController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private VersionService versionService;
	
	@RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public String getAll(HttpServletRequest request) {
		List<VersionVo> voList = null;
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
	
	        Return<List<VersionVo>> ret = versionService.getAll();
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        voList = ret.getData();
		} catch(Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}

        return Constant.pack(voList);
    }
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(HttpServletRequest request) {
		VersionVo vo = null;
		try {
	        VersionEditAcceptMsg acceptMsg = Constant.subPack(request, VersionEditAcceptMsg.class);
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
	
	        Return<VersionVo> ret = versionService.edit(acceptMsg.getId(), acceptMsg.getVersion(), acceptMsg.getState());
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        vo = ret.getData();
		} catch(Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}

        return Constant.pack(vo);
    }
}
