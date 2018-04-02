package com.scd.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.RichAcceptMsg;
import com.scd.admin.controller.accept.RichSelectAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.service.RichService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/rich")
public class RichController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RichService richService;

	@RequestMapping(value = "/selectOne", method = RequestMethod.POST)
    public String selectOne(HttpServletRequest request) {
		RichBo returnRich = null;
		try {
	        RichSelectAcceptMsg acceptMsg = Constant.subPack(request, RichSelectAcceptMsg.class);
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
			
	        Return<RichBo> ret = richService.selectOne(acceptMsg.getType(), acceptMsg.getForeignId());
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        returnRich = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}

        return Constant.pack(returnRich);
    }
	

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(HttpServletRequest request) {
		RichBo returnRich = null;
		try {
	        RichAcceptMsg acceptMsg = Constant.subPack(request, RichAcceptMsg.class);
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
	      
	      long type = acceptMsg.getType();
	      long foreignId = acceptMsg.getId();
	      long linkId = foreignId;
	      if (type == Type.RICH_TYPE_COMMUNITY) {
	    	  linkId = acceptMsg.getLinkId();
	      }
			
			RichBo richBo = new RichBo(type, foreignId, linkId, acceptMsg.getData());
	        Return<RichBo> ret = richService.submit(richBo);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        returnRich = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}

        return Constant.pack(returnRich);
    }
}
