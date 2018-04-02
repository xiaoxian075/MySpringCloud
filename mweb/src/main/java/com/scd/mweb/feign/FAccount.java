package com.scd.mweb.feign;

import com.scd.mweb.hystrix.HAccount;
import com.scd.joggle.pojo.bo.AccountBalanceBo;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.bo.CollectBo;
import com.scd.joggle.pojo.po.AccountLoginPo;
import com.scd.joggle.pojo.po.AccountPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${feign.coredb}", fallback = HAccount.class)
public interface FAccount {

	@RequestMapping(value = "/account/register")
	boolean register(@RequestBody AccountBo accountBo);

	@RequestMapping(value = "/account/getAccountByLoginName")
	AccountBo getAccountByLoginName(@RequestParam(value = "loginName")String loginName);

	@RequestMapping(value = "/account/getInfoByAccount")
	AccountBo getInfoByAccount(@RequestParam(value = "account") String account);
	
	@RequestMapping(value = "/account/getAccountBalanceByAccount")
	AccountBalanceBo getAccountBalanceByAccount(@RequestParam(value = "account") String account);
	
	@RequestMapping(value = "/account/findByLoginName")
	AccountLoginPo findByLoginName(@RequestParam(value = "loginName")String loginName);

	@RequestMapping(value = "/account/resetPwd")
	Return<AccountLoginPo> resetPwd(
			@RequestParam(value = "loginName")String loginName, 
			@RequestParam(value = "newPassword")String newPassword);

	@RequestMapping(value = "/account/getRefereeList")
	Return<PageInfo<AccountBo>> getRefereeList(@RequestParam(value = "account") String account,
												@RequestParam(value = "page") int page,
												@RequestParam(value = "size") int size);

	@RequestMapping(value = "/account/getCollectList")
	Return<PageInfo<CollectBo>> getCollectList(@RequestParam(value = "account") String account,
											   @RequestParam(value = "page") int page,
											   @RequestParam(value = "size") int size);

	@RequestMapping(value = "/account/setPayPwd")
	boolean setPayPwd(@RequestParam(value = "account") String account,
					  @RequestParam(value = "payPwd") String payPwd);

	@RequestMapping(value = "/account/updatePwd")
	boolean updatePwd(@RequestParam(value = "account") String account,
					  @RequestParam(value = "newPassword") String newPassword);

	@RequestMapping(value = "/account/updateAccountInfo")
	Return<AccountPo> updateAccountInfo(@RequestParam(value = "account") String account,
								@RequestParam(value = "nickName") String nickName,
								@RequestParam(value = "pic") String pic,
								@RequestParam(value = "sex") int sex);
}
