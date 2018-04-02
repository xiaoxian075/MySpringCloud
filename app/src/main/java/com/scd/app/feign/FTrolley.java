package com.scd.app.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HTrolley;
import com.scd.joggle.pojo.po.TrolleyPo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HTrolley.class)
public interface FTrolley {

	@RequestMapping(value = "/trolley/getList")
	Return<List<TrolleyPo>> getList(@RequestParam(value = "account")String account);

	@RequestMapping(value = "/trolley/add")
	Return<TrolleyPo> add(@RequestParam(value = "odd")String odd, @RequestParam(value = "account")String account, @RequestParam(value = "communityId")long communityId, @RequestParam(value = "attrId")long attrId, @RequestParam(value = "num")int num);

	@RequestMapping(value = "/trolley/editNum")
	Return<TrolleyPo> editNum(@RequestParam(value = "id")long id, @RequestParam(value = "account")String account, @RequestParam(value = "num")int num);

	@RequestMapping(value = "/trolley/batchDel")
	Return<List<Long>> batchDel(@RequestParam(value = "account")String account, @RequestBody List<Long> idList);

}
