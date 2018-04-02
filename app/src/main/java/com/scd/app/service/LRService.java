package com.scd.app.service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FAccount;
import com.scd.app.third.ThirdYft;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.po.AccountLoginPo;
import com.scd.sdk.util.MD5Util;
import com.scd.sdk.util.pojo.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LRService {
	
//	@Autowired
//	private AccountLogin accountLogin;
	
	@Autowired
	private FAccount fAccount;

	public Return<AccountLoginPo> getAccountLogin(String loginName) {
		AccountLoginPo accountLoginPo = fAccount.findByLoginName(loginName);
		if (accountLoginPo == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		return Constant.createReturn(accountLoginPo);
	}

	public Return<Object> register(String loginName, String password, String referee) {
		
		password = MD5Util.encodeByMD5(password);
		if (password == null) {
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		if (referee == null) {
			referee = "";
		}
		
		AccountBo accountBo = fAccount.getAccountByLoginName(loginName);
		if (accountBo != null) {
			return Constant.createReturn(ErrorCom.LOGIN_NAME_HAS_EXIST);
		}

		accountBo = new AccountBo(
				0,
				loginName,
				loginName,
				password,
				"",
				"",
				Type.SEX_NONE,
				Type.MEMBER_NORMAL,
				BigDecimal.ZERO,
				0L,
				referee,
				System.currentTimeMillis(),
				0L,
				Type.PAY_STATE_USABLE,
				null,
				Type.NO_SET_PAY_PASSWORD,
				Type.ACCOUNT_STATE_USABLE,
				BigDecimal.ZERO,
				BigDecimal.ZERO
				);
		
		// 云付通进行注册
		Return<Object> ret = ThirdYft.getInstance().register(accountBo);
		if (Return.isErr(ret)) {
			if (ret.getCode() != 3000) {	// 3000表示账号已被注册
				return ret;
			}
			
		}
		
		// 本地注册
		if (!fAccount.register(accountBo)) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		
		return Constant.createReturn();
	}

	public Return<AccountLoginPo> resetPwd(String loginName, String newPassword) {
		return fAccount.resetPwd(loginName, newPassword);
		
//		AccountLoginPo accountLoginPo = fAccount.findByLoginName(loginName);
//		if (accountLoginPo == null) {
//			return Constant.createReturn(ErrorCom.NOT_EXIST);
//		}
//		
//		newPassword = MD5Util.encodeByMD5(newPassword);
//		accountLoginPo.setPassword(newPassword);
//		accountLoginPo.setUpdateTime(System.currentTimeMillis());
//		if (!fAccount.resetPwd(accountLoginPo)) {
//			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
//		}
//		
//		return Constant.createReturn(accountLoginPo);
	}

	public Return<Object> updatePwd(String account, String newPassword, String oldPassword) {
		AccountBo accountBo = fAccount.getInfoByAccount(account);
		if(accountBo == null){
			return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
		}
		if(!oldPassword.equals(accountBo.getPassword())){
			return Constant.createReturn(ErrorCom.PASSWORD_ERROR_MATCH);
		}
		boolean result = fAccount.updatePwd(account,newPassword);
		if(!result){
			return Constant.createReturn(ErrorCom.UPDATE_PASSWORD_ERROR);
		}
		return Constant.createReturn();
	}
}
