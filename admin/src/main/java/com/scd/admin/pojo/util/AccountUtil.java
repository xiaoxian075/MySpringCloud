package com.scd.admin.pojo.util;

import com.scd.admin.pojo.vo.AccountVo;
import com.scd.joggle.pojo.bo.AccountBo;

import java.util.ArrayList;
import java.util.List;

public class AccountUtil {

    public static List<AccountVo> change(List<AccountBo> poList) {
    	if (poList == null) {
    		return null;
    	}
    	
    	List<AccountVo> voList = new ArrayList<AccountVo>();
    	for (AccountBo bo : poList) {
    		AccountVo vo = change(bo);
    		if (vo != null) {
    			voList.add(vo);
    		}
    	}
    	
    	return voList;
    }

    public static AccountVo change(AccountBo bo) {
		if (bo == null) {
			return null;
		}
		
		return new AccountVo(
				bo.getId(),
				bo.getAccount(),
				bo.getLoginName(),
				bo.getNickName(),
				bo.getHeadUrl(),
				bo.getSex(),
				bo.getLevel(),
				bo.getBalance(),
				bo.getYunBalance(),
				bo.getReferee(),
				bo.getCreateTime(),
				bo.getLastLoginTime(),
				bo.getPayState(),
				bo.getIsPushMsg(),
				bo.getAccountState()
				);
	}
}
