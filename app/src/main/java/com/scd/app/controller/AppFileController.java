package com.scd.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.app.constant.Constant;
import com.scd.app.mgr.FileMgr;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.service.AccountService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.FileInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/file")
public class AppFileController {
	
    @Resource
    private AccountService accountService;
    
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request) {
		String sessionId = request.getParameter("sessionId");
		Session session = SessionMgr.getInstance().get(sessionId);
		if(session == null){
			return Constant.pack(ErrorCom.GET_LOGIN);
		}
		String account = session.getAccount();
		String picInfo = request.getParameter("picInfo");
		Return<FileInfo> ret = FileMgr.getInstance().saveSowing(picInfo);
		if (Return.isErr(ret)) {
			return Constant.pack(ErrorCom.ERROR);
		}
		
		accountService.updateAccountInfo(account, null, ret.getData().getUrl(), 0);
		return Constant.noPack(ret.getData());
	}
}
