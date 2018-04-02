package com.scd.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.GetPicsAcceptMsg;
import com.scd.app.controller.accept.LoginAcceptMsg;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.util.AccountUtil;
import com.scd.app.pojo.vo.AccountVo;
import com.scd.app.pojo.vo.CarouselVo;
import com.scd.app.service.MainService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.sdk.util.RandomUtil;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import com.scd.sdk.util.pojo.ZeroAcceptMsg;

@RestController
@RequestMapping(value = "/mn")
public class MainController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Content-Type:application/x-www-form-urlencoded
	@Resource
	private MainService mainService;
	
	/**
	 * 获取session
	 * @return	session id
	 */
	@RequestMapping(value = "/getSession", method = RequestMethod.POST)
	public String firstResp(HttpServletRequest request) {
		String clientIp = request.getRemoteAddr();
		String sessionId = RandomUtil.getUuid();
		if (clientIp == null || clientIp.length() == 0 || sessionId == null || sessionId.length() == 0) {
			return "";
		}
		Session session = new Session(clientIp, sessionId, 30000);
		SessionMgr.getInstance().add(session);
		
		System.out.println("session id is " + sessionId);
		
		return sessionId;
	}
	
	/**
	 * 登入
	 * @param 	loginName	登入名
	 * @param 	password	登入密码
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request) {
		LoginAcceptMsg acceptMsg = Constant.subPack(request, LoginAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.checkLoginName()) {
			return Constant.pack(ErrorCom.LOGIN_NAME_ERROR);
		}
		if (!acceptMsg.checkPassword()) {
			return Constant.pack(ErrorCom.PASSWORD_ERROR);
		}
		
		Session session = SessionMgr.getInstance().getSession(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_SESSION);
		}

		// 登入处理
		String loginName = acceptMsg.getLoginName();
		String password = acceptMsg.getPassword();
		Return<AccountBo> ret = mainService.login(loginName, password);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		AccountBo accountBo = ret.getData();
		if (accountBo == null) {
			return Constant.pack(ErrorCom.ERROR);
		}
		
		String account = accountBo.getAccount();
		
		// 踢掉已登入账户
		String oldSession = SessionMgr.getInstance().getSessionByAccount(session.getSessionId(), account);
		if (oldSession != null) {
			SessionMgr.getInstance().remove(oldSession);
		}	
		session.setAccount(account);
		
		AccountVo vo = AccountUtil.change(accountBo);

		return Constant.pack(vo);
	}
	

	/**
	 * 退出登入
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request) {
		try {
			ZeroAcceptMsg acceptMsg = Constant.subPack(request, ZeroAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			
			Session session = SessionMgr.getInstance().getSession(acceptMsg);
			if (session != null) {
				SessionMgr.getInstance().remove(session.getSessionId());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}

		return Constant.pack();
	}
	
	/**
	 * 获取轮播图
	 * @param 	type	类型 1：云社区 2：零距离
	 * @return
	 */
	@RequestMapping(value = "/getPics", method = RequestMethod.POST)
	public String getPics(HttpServletRequest request) {
		GetPicsAcceptMsg acceptMsg = Constant.subPack(request, GetPicsAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		Session session = SessionMgr.getInstance().checkSession(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_SESSION);
		}
		
		// 根据类型获取轮播图
		int type = acceptMsg.getType();
		Return<CarouselVo> ret = mainService.getPics(type);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
}
