package com.scd.coredb.pojo.util;

import com.scd.coredb.pojo.db.TAccountBalance;
import com.scd.coredb.pojo.db.TAccountInfo;
import com.scd.coredb.pojo.db.TAccountLogin;
import com.scd.joggle.pojo.bo.AccountBo;

public class AccountUtil {

	public static AccountBo change(TAccountLogin tAccountLogin, TAccountInfo tAccountInfo, TAccountBalance tAccountBalance) {
		if (tAccountLogin == null || tAccountInfo == null || tAccountBalance == null) {
			return null;
		}
		return new AccountBo(
				tAccountInfo.getId(),
				tAccountInfo.getAccount(),
				tAccountLogin.getLoginName(),
				tAccountLogin.getPassword(),
				tAccountInfo.getNickName(),
				tAccountInfo.getHeadUrl(),
				tAccountInfo.getSex(),
				tAccountInfo.getLevel(),
				tAccountBalance.getBalance(),
				tAccountBalance.getYunBalance(),
				tAccountInfo.getReferee(),
				tAccountInfo.getCreateTime(),
				tAccountLogin.getLastLoginTime(),
				tAccountBalance.getPayState(),
				tAccountInfo.getPayPassword(),
				tAccountInfo.getIsPushMsg(),
				tAccountInfo.getAccountState(), 
				tAccountBalance.getTotalConsume(),
				tAccountBalance.getTotalRecharge()
		);
	}

}
