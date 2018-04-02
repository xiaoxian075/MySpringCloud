package com.scd.mweb.hystrix;

import com.scd.mweb.constant.Constant;
import com.scd.mweb.feign.FAccount;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.AccountBalanceBo;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.bo.CollectBo;
import com.scd.joggle.pojo.po.AccountLoginPo;
import com.scd.joggle.pojo.po.AccountPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class HAccount implements FAccount {
	
	@Override
	public boolean register(AccountBo accountBo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AccountBo getAccountByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountBo getInfoByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccountLoginPo findByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Return<AccountLoginPo> resetPwd(String loginName, String newPassword) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<PageInfo<AccountBo>> getRefereeList(@RequestParam(value = "account") String account,
													  @RequestParam(value = "page") int page,
													  @RequestParam(value = "size") int size) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<PageInfo<CollectBo>> getCollectList(@RequestParam(value = "account") String account,
													  @RequestParam(value = "page") int page,
													  @RequestParam(value = "size") int size) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public boolean setPayPwd(@RequestParam(value = "account") String account,
									@RequestParam(value = "payPwd") String payPwd) {
		return false;
	}

	@Override
	public boolean updatePwd(@RequestParam(value = "account") String account,
							 @RequestParam(value = "newPassword") String newPassword) {
		return false;
	}

	@Override
	public Return<AccountPo> updateAccountInfo(
			                           @RequestParam(value = "account") String account,
									   @RequestParam(value = "nickName") String nickName,
									   @RequestParam(value = "pic") String pic,
									   @RequestParam(value = "sex") int sex) {

		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public AccountBalanceBo getAccountBalanceByAccount(String account) {
		// TODO Auto-generated method stub
		return null;
	}

}
