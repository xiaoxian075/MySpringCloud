package com.scd.admin.controller;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.MessageAddAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.MessageVo;
import com.scd.admin.service.MessageService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.IdlistAcceptMsg;
import com.scd.sdk.util.pojo.PageAcceptMsg;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/message")
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MessageService messageService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(HttpServletRequest request) {
    	PageInfo<MessageVo> pageInfo = null;
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
	            return Constant.pack(ErrorCom.GET_LOGIN);
	        }
	
	        int page = acceptMsg.getPage();
	        int size = acceptMsg.getSize();
	        Return<PageInfo<MessageVo>> ret = messageService.list(page, size);
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


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest request) {
    	try {
	        MessageAddAcceptMsg acceptMsg = Constant.subPack(request, MessageAddAcceptMsg.class);
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
	        
	        int msgType = acceptMsg.getMsgType();
	        String msgTitle = acceptMsg.getMsgTitle();
	        String introduction = acceptMsg.getIntroduction();
	        String msgContent = acceptMsg.getMsgContent();
	        Return<Object> ret = messageService.add(msgType, msgTitle, introduction, msgContent);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
    	} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}

        return Constant.pack();
    }


    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public String del(HttpServletRequest request) {
    	long returnId = 0;
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
	        Return<Long> ret = messageService.del(id);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        returnId = ret.getData();
    	} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}

        return Constant.pack(returnId);
    }

    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
    public String batchDel(HttpServletRequest request) {
    	List<Long> returnIdList = null;
    	try {
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
	        Return<List<Long>> ret = messageService.batchDel(idList);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        returnIdList = ret.getData();
    	} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}	        

        return Constant.pack(returnIdList);
    }


    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public String push(HttpServletRequest request) {
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
	        Return<Object> ret = messageService.push(id);
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
