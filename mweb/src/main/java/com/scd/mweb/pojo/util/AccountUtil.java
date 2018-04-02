package com.scd.mweb.pojo.util;

import com.scd.mweb.pojo.vo.AccountVo;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.AccountBo;

public class AccountUtil {

	public static AccountVo change(AccountBo accountBo) {
		if (accountBo == null) {
			return null;
		}
		return new AccountVo(
			accountBo.getId(),
			accountBo.getAccount(),
			accountBo.getLoginName(),
			accountBo.getNickName(),
			accountBo.getHeadUrl(),
			accountBo.getSex(),
			accountBo.getLevel(),
			accountBo.getBalance(),
			accountBo.getYunBalance(),
			accountBo.getReferee(),
			accountBo.getCreateTime(),
			accountBo.getLastLoginTime(),
			accountBo.getPayState(),
			accountBo.getIsPushMsg(),
			accountBo.getAccountState(),
			accountBo.getPayPassword() == null||("").equals(accountBo.getPayPassword())? Type.NO_SET_PAY_PASSWORD:Type.IS_SET_PAY_PASSWORD
		);
	}
}
