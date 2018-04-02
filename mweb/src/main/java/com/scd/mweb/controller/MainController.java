package com.scd.mweb.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.controller.accept.GetPicsAcceptMsg;
import com.scd.mweb.controller.accept.LoginAcceptMsg;
import com.scd.mweb.pojo.util.AccountUtil;
import com.scd.mweb.pojo.vo.AccountVo;
import com.scd.mweb.pojo.vo.CarouselVo;
import com.scd.mweb.service.MainService;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/mn")
public class MainController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Content-Type:application/x-www-form-urlencoded
	@Resource
	private MainService mainService;
	

	/**
	 * 登入
	 * @param 	loginName	登入名
	 * @param 	password	登入密码
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request) {
		AccountVo vo = null;
		try {
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
			
			vo = AccountUtil.change(accountBo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}

		return Constant.pack(vo);
	}
	
	/**
	 * 获取轮播图
	 * @param 	type	类型 1：云社区 2：零距离
	 * @return
	 */
	@RequestMapping(value = "/getPics", method = RequestMethod.POST)
	public String getPics(HttpServletRequest request) {
		CarouselVo vo = null;
		try {
			GetPicsAcceptMsg acceptMsg = Constant.subPack(request, GetPicsAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			
			// 根据类型获取轮播图
			int type = acceptMsg.getType();
			Return<CarouselVo> ret = mainService.getPics(type);
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
