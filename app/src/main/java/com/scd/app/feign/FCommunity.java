package com.scd.app.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HCommunity;
import com.scd.joggle.pojo.po.CommunityPo;

@FeignClient(value = "${feign.coredb}", fallback = HCommunity.class)
public interface FCommunity {

	@RequestMapping(value = "/community/getPageInfoIncludeState")
	List<CommunityPo> getPageInfoIncludeState(@RequestParam(value = "account")String account, @RequestParam(value = "type")int type, @RequestParam(value = "page") int page, @RequestParam(value = "size") int size);

	@RequestMapping(value = "/community/findOne")
	CommunityPo findOne(@RequestParam(value = "id")long id);

	@RequestMapping(value = "/community/insertOne")
	boolean insertOne(@RequestBody CommunityPo communityPo);

	@RequestMapping(value = "/community/updateOne")
	boolean updateOne(@RequestBody CommunityPo communityPo);

	@RequestMapping(value = "/community/praise")
	long praise(@RequestParam(value = "account")String account, @RequestParam(value = "id")long id, @RequestParam(value = "type")int type);

}
