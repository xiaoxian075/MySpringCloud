package com.scd.mweb.controller;

import com.scd.mweb.constant.Constant;
import com.scd.mweb.controller.accept.RegOfCheckCodeAcceptMsg;
import com.scd.mweb.controller.accept.RegOfGetCodeAcceptMsg;
import com.scd.mweb.controller.accept.RegOfPwdARefereeAcceptMsg;
import com.scd.mweb.mgr.RegisterMgr;
import com.scd.mweb.pojo.bo.RegisterCodeBo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.AccountLoginPo;
import com.scd.mweb.service.LRService;
import com.scd.mweb.third.ThirdChit;
import com.scd.sdk.util.GeneralProduceUtil;
import com.scd.sdk.util.pojo.Return;

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
		
		long curTime = System.currentTimeMillis();

		// 验证是否频繁操作
		RegisterCodeBo registerCodeBo = RegisterMgr.getInstance().get(acceptMsg.getPhone());
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
		
		
		// 将验证码存到session中，供下一步验证使用
		if (registerCodeBo != null) {
			registerCodeBo.setPhone(phone);
			registerCodeBo.setCode(code);
			registerCodeBo.setTimestamp(curTime);
		} else {
			registerCodeBo = new RegisterCodeBo(Constant.REGISTER_CODE, phone, code, curTime);
		}
		RegisterMgr.getInstance().add(registerCodeBo);
		
		logger.info("找回密码之获取验证码--电话号码：" + phone + ", 验证码：" + code);
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
		

		long curTime = System.currentTimeMillis();
		
		// 获取Session对象
		RegisterCodeBo registerCodeBo = RegisterMgr.getInstance().get(acceptMsg.getPhone());
		//RegisterCodeBo registerCodeBo = (RegisterCodeBo) session.getObj(Constant.REGISTER_CODE);
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
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		long curTime = System.currentTimeMillis();
		
		// 获取Session对象
		RegisterCodeBo registerCodeBo = RegisterMgr.getInstance().get(acceptMsg.getPhone());
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
		
		RegisterMgr.getInstance().remove(acceptMsg.getPhone());
		
		return Constant.pack();
	}
	
}
