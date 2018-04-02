package com.scd.admin.feign;

import com.scd.admin.hystrix.HAccount;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.param.AccountSelectParam;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${feign.coredb}", fallback = HAccount.class)
public interface FAccount {
	
	@RequestMapping(value = "/account/getpage")
	Return<PageInfo<AccountBo>> getPage(@RequestBody AccountSelectParam param);

    @RequestMapping(value = "/account/setState")
    Return<Long> setState(@RequestParam(value = "id")long id, @RequestParam(value = "type")int type);
}
