package com.scd.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.VersionTypeAcceptMsg;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.vo.VersionVo;
import com.scd.app.service.VersionService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/version")
public class VersionController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private VersionService versionService;
	
	@RequestMapping(value = "/getVersion", method = RequestMethod.POST)
	public String getVersion(HttpServletRequest request) {
		VersionVo vo = null;
		try {
			VersionTypeAcceptMsg acceptMsg = Constant.subPack(request, VersionTypeAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkSession(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_LOGIN);
			}
	
			Return<VersionVo> ret = versionService.getVersion(acceptMsg.getType());
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
