package com.scd.admin.hystrix;

import org.springframework.stereotype.Component;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FAccount;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.param.AccountSelectParam;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HAccount implements FAccount {

	@Override
	public Return<PageInfo<AccountBo>> getPage(AccountSelectParam param) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

    @Override
    public Return<Long> setState(long id, int type) {
        return Constant.createReturn(ErrorCom.FEIGN_ERROR);
    }

}
