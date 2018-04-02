package com.scd.app.controller;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.*;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.bo.RegisterCodeBo;
import com.scd.app.pojo.bo.ResetPwdBo;
import com.scd.app.service.LRService;
import com.scd.app.third.ThirdChit;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.AccountLoginPo;
import com.scd.sdk.util.GeneralProduceUtil;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @ClassName LRController
 * @Description 登入注册忘记密码
 * @author chenjx
 * @date 2018-02-05
 */

@RestController
@RequestMapping(value = "/lr")
public class LRController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private LRService lrService;
	
	
	
	/**
	 * 注册之获取验证码
	 * @return	验证码
	 */
	@RequestMapping(value = "/regOfGetCode", method = RequestMethod.POST)
	public String regOfGetCode(HttpServletRequest request) {
		RegOfGetCodeAcceptMsg acceptMsg = Constant.subPack(request, RegOfGetCodeAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PHONE_NUMBER_ERROR);
		}
		
		Session session = SessionMgr.getInstance().getSession(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_SESSION);
		}
		
		long curTime = System.currentTimeMillis();
		
		// 验证是否频繁操作
		RegisterCodeBo registerCodeBo = (RegisterCodeBo) session.getObj(Constant.REGISTER_CODE);
		if (registerCodeBo != null && registerCodeBo.getType() == Constant.REGISTER_CODE) {
			if (registerCodeBo.getTimestamp() + 60000 > curTime) {
				return Constant.pack(ErrorCom.GET_CODE_FREQUENTLY);
			}
		}
		
		
		// 验证手机号是否已注册
		String phone = acceptMsg.getPhone();
		Return<AccountLoginPo> ret = lrService.getAccountLogin(phone);
		if (Return.isErr(ret)) {
			if (ret == null || ret.getCode() != ErrorCom.NOT_EXIST.getCode()) {
				return Constant.pack(ret);
			}
		} else {
			return Constant.pack(ErrorCom.PHONE_HAS_REGISTER);
		}
		
		
		// 产生验证码
		String code = GeneralProduceUtil.createCode();
		if (code == null) {
			return Constant.pack(ErrorCom.GET_CHECK_CODE_ERROR);
		}
		
		
		// 发送短信（验证码）至手机号码
		if (!ThirdChit.getInstance().sendRegister(phone, code)) {
			return Constant.pack(ErrorCom.SEND_CODE_ERROR);
		}
//		if (!Rabbitmq.getInstance().sendChitRegister(phone, code)) {
//			return Constant.pack(ErrorCom.SEND_CODE_ERROR);
//		}
		
		
		
		// 将验证码存到session中，供下一步验证使用
		if (registerCodeBo != null) {
			registerCodeBo.setPhone(phone);
			registerCodeBo.setCode(code);
			registerCodeBo.setTimestamp(curTime);
		} else {
			registerCodeBo = new RegisterCodeBo(Constant.REGISTER_CODE, phone, code, curTime);
		}
		session.addObj(Constant.REGISTER_CODE, registerCodeBo);
		
		logger.info("注册之获取验证码--电话号码：" + phone + ", 验证码：" + code);
		return Constant.pack();
	}
	
	/**
	 * 注册之核对验证码
	 * @return
	 */
	@RequestMapping(value = "/regOfCheckCode", method = RequestMethod.POST)
	public String regOfCheckCode(HttpServletRequest request) {
		RegOfCheckCodeAcceptMsg acceptMsg = Constant.subPack(request, RegOfCheckCodeAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.GET_CHECK_CODE_ERROR);
		}
		
		Session session = SessionMgr.getInstance().getSession(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_SESSION);
		}
		

		long curTime = System.currentTimeMillis();
		
		// 获取Session对象
		RegisterCodeBo registerCodeBo = (RegisterCodeBo) session.getObj(Constant.REGISTER_CODE);
		if (registerCodeBo == null || registerCodeBo.getType() != Constant.REGISTER_CODE) {
			return Constant.pack(ErrorCom.FIRST_GET_CODE);
		}
		// 检查是否过期
		if (registerCodeBo.getTimestamp() + 60000 < curTime) {
			return Constant.pack(ErrorCom.CODE_EXCEED);
		}
		
		// 核对验证码
		String code = acceptMsg.getCode();
		if (!code.equals(registerCodeBo.getCode())) {
			return Constant.pack(ErrorCom.CODE_ERROR);
		}
		
		// 更新对象时间戮
		registerCodeBo.setTimestamp(curTime);
		
		return Constant.pack(code);
	}
	
	/**
	 * 注册之设置密码与推荐人
	 * @return
	 */
	@RequestMapping(value = "/regOfPwdAReferee", method = RequestMethod.POST)
	public String regOfPwdAReferee(HttpServletRequest request) {
		RegOfPwdARefereeAcceptMsg acceptMsg = Constant.subPack(request, RegOfPwdARefereeAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.checkPassword()) {
			return Constant.pack(ErrorCom.PASSWORD_ERROR);
		}
		if (!acceptMsg.checkReferee()) {
			return Constant.pack(ErrorCom.REFEREE_ERROR);
		}
		Session session = SessionMgr.getInstance().getSession(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_SESSION);
		}
		
		long curTime = System.currentTimeMillis();
		
		// 获取Session对象
		RegisterCodeBo registerCodeBo = (RegisterCodeBo) session.getObj(Constant.REGISTER_CODE);
		if (registerCodeBo == null || registerCodeBo.getType() != Constant.REGISTER_CODE) {
			return Constant.pack(ErrorCom.FIRST_GET_CODE);
		}
		// 检查是否过期
		if (registerCodeBo.getTimestamp() + 120000 < curTime) {
			return Constant.pack(ErrorCom.CODE_EXCEED);
		}
		
		
		// 处理密码设置与推荐人设置
		String phone = registerCodeBo.getPhone();
		String password = acceptMsg.getPassword();
		String referee = acceptMsg.getReferee();
		Return<Object> ret = lrService.register(phone, password, referee);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		
		
		return Constant.pack();
	}
	
	/**
	 * 找回密码之获取验证码
	 * @return	验证码
	 */
	@RequestMapping(value = "/rpOfGetCode", method = RequestMethod.POST)
	public String rpOfGetCode(HttpServletRequest request) {
		RpOfGetCodeAcceptMsg acceptMsg = Constant.subPack(request, RpOfGetCodeAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PHONE_NUMBER_ERROR);
		}
		
		Session session = SessionMgr.getInstance().getSession(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_SESSION);
		}
		
		// 验证是否频繁操作
		ResetPwdBo resetPwdBo = (ResetPwdBo) session.getObj(Constant.RESETPWD_CODE);
		if (resetPwdBo != null && resetPwdBo.getType() == Constant.RESETPWD_CODE) {
			if (resetPwdBo.getTimestamp() + 60000 > System.currentTimeMillis()) {
				return Constant.pack(ErrorCom.GET_CODE_FREQUENTLY);
			}
		}
		
		long curTime = System.currentTimeMillis();

		// 验证手机号是否已注册
		String phone = acceptMsg.getPhone();
		Return<AccountLoginPo> ret = lrService.getAccountLogin(phone);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		
		// 产生验证码
		String code = GeneralProduceUtil.createCode();
		if (code == null) {
			return Constant.pack(ErrorCom.GET_CHECK_CODE_ERROR);
		}
		
		// 发送短信（验证码）至手机号码
		if (!ThirdChit.getInstance().sendResetPwd(phone, code)) {
			return Constant.pack(ErrorCom.SEND_CODE_ERROR);
		}
//		if (!Rabbitmq.getInstance().sendChitGetBackPassword(phone, code)) {
//			return Constant.pack(ErrorCom.SEND_CODE_ERROR);
//		}
		
		
		// 将验证码存到session中，供下一步验证使用
		if (resetPwdBo != null) {
			resetPwdBo.setPhone(phone);
			resetPwdBo.setCode(code);
			resetPwdBo.setTimestamp(curTime);
		} else {
			resetPwdBo = new ResetPwdBo(Constant.RESETPWD_CODE, phone, code, curTime);
		}
		session.addObj(Constant.RESETPWD_CODE, resetPwdBo);
		
		logger.info("找回密码之获取验证码--电话号码：" + phone + ", 验证码：" + code);
		return Constant.pack();
	}
	
	/**
	 * 找回密码之核对验证码
	 * @return
	 */
	@RequestMapping(value = "/rpOfCheckCode", method = RequestMethod.POST)
	public String rpOfCheckCode(HttpServletRequest request) {
		RpOfCheckCodeAcceptMsg acceptMsg = Constant.subPack(request, RpOfCheckCodeAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.GET_CHECK_CODE_ERROR);
		}
		
		Session session = SessionMgr.getInstance().getSession(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_SESSION);
		}
		

		long curTime = System.currentTimeMillis();
		
		// 获取Session对象
		ResetPwdBo resetPwdBo = (ResetPwdBo) session.getObj(Constant.RESETPWD_CODE);
		if (resetPwdBo == null || resetPwdBo.getType() != Constant.RESETPWD_CODE) {
			return Constant.pack(ErrorCom.FIRST_GET_CODE);
		}
		// 检查是否过期
		if (resetPwdBo.getTimestamp() + 60000 < curTime) {
			return Constant.pack(ErrorCom.CODE_EXCEED);
		}
		
		// 核对验证码
		String code = acceptMsg.getCode();
		if (!code.equals(resetPwdBo.getCode())) {
			return Constant.pack(ErrorCom.CODE_ERROR);
		}
		
		// 更新对象时间戮
		resetPwdBo.setTimestamp(curTime);
		
		return Constant.pack();
	}
	
	/**
	 * 找回密码之重置密码
	 * @return
	 */
	@RequestMapping(value = "/rpOfResetPwd", method = RequestMethod.POST)
	public String rpOfResetPwd(HttpServletRequest request) {
		RpOfResetPwdAcceptMsg acceptMsg = Constant.subPack(request, RpOfResetPwdAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PASSWORD_ERROR);
		}
		Session session = SessionMgr.getInstance().getSession(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_SESSION);
		}
		
		long curTime = System.currentTimeMillis();
		
		// 获取Session对象
		ResetPwdBo resetPwdBo = (ResetPwdBo) session.getObj(Constant.RESETPWD_CODE);
		if (resetPwdBo == null || resetPwdBo.getType() != Constant.RESETPWD_CODE) {
			return Constant.pack(ErrorCom.FIRST_GET_CODE);
		}
		// 检查是否过期
		if (resetPwdBo.getTimestamp() + 120000 < curTime) {
			return Constant.pack(ErrorCom.CODE_EXCEED);
		}
		
		
		// 重置登入密码
		String phone = resetPwdBo.getPhone();
		String newPassword = acceptMsg.getNewPassword();
		Return<AccountLoginPo> ret = lrService.resetPwd(phone, newPassword);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack();
	}

	/**
	 * 修改登录密码
	 * @return
	 */
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	public String updatePwd(HttpServletRequest request) {
		UpdatePwdAcceptMsg acceptMsg = Constant.subPack(request, UpdatePwdAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PASSWORD_ERROR);
		}
		Session session = SessionMgr.getInstance().getSession(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_SESSION);
		}

		String oldPassword = acceptMsg.getOldPassword();
		String newPassword = acceptMsg.getNewPassword();

		Return<Object> ret = lrService.updatePwd(session.getAccount(),newPassword,oldPassword);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}

		return Constant.pack();
	}
}
