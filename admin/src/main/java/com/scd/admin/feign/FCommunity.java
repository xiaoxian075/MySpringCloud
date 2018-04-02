package com.scd.admin.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HCommunity;
import com.scd.joggle.pojo.po.CommunityPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HCommunity.class)
public interface FCommunity {

	@RequestMapping(value = "/community/list")
	Return<PageInfo<CommunityPo>> list(@RequestParam(value = "page")int page, @RequestParam(value = "size")int size);

	@RequestMapping(value = "/community/add")
	Return<Object> add(@RequestParam(value = "title")String title, @RequestParam(value = "type")int type, @RequestParam(value = "url")String url);

	@RequestMapping(value = "/community/edit")
	Return<Object> edit(@RequestParam(value = "id")long id, @RequestParam(value = "title")String title, @RequestParam(value = "type")int type, @RequestParam(value = "url")String url);

	@RequestMapping(value = "/community/del")
	Return<Long> del(@RequestParam(value = "id")long id);

	@RequestMapping(value = "/community/batchDel")
	Return<List<Long>> batchDel(@RequestBody List<Long> idList);

}
