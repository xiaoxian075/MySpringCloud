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
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.service.CommonService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/common")
public class CommonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private CommonService commonService;

	@RequestMapping(value = "/getAreaChild", method = RequestMethod.POST)
	public String getAreaChild(HttpServletRequest request) {
		List<AreaShortPojo> voList = null;
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
			Return<List<AreaShortPojo>> ret = commonService.getAreaChild(id);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			voList= ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(voList);
	}
}
