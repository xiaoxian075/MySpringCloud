package com.scd.app.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HAddress;
import com.scd.joggle.pojo.po.AddressPo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HAddress.class)
public interface FAddress {

	@RequestMapping(value = "/address/getListByAccount")
	Return<List<AddressPo>> getListByAccount(@RequestParam(value = "account")String account);

	@RequestMapping(value = "/address/add")
	Return<AddressPo> add(@RequestBody AddressPo addressPo);

	@RequestMapping(value = "/address/edit")
	Return<AddressPo> edit(@RequestBody AddressPo addressPo);

	@RequestMapping(value = "/address/setDefault")
	Return<Object> setDefault(@RequestParam(value = "id")long id, @RequestParam(value = "state")int state);

	@RequestMapping(value = "/address/del")
	Return<Object> del(@RequestParam(value = "id")long id);

	@RequestMapping(value = "/address/getDefault")
	Return<AddressPo> getDefault(@RequestParam(value = "account")String account);

}
