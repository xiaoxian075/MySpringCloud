package com.scd.app.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HCommodity;
import com.scd.joggle.pojo.po.CommodityPo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HCommodity.class)
public interface FCommodity {
	
	@RequestMapping(value = "/commodity/getOne")
	Return<CommodityPo> getOne(@RequestParam(value = "id")long id);
	
	@RequestMapping(value = "/commodity/getPageIncludeAttr")
	Return<List<CommodityPo>> getPageIncludeAttr(@RequestParam(value = "shopId")long shopId, @RequestParam(value = "type")int type, @RequestParam(value = "page")int page, @RequestParam(value = "size")int size);

	@RequestMapping(value = "/commodity/getAssort")
	Return<List<CommodityPo>> getAssort(@RequestParam(value = "id")long id);
	
	@RequestMapping(value = "/commodity/getRecommend")
	public Return<List<CommodityPo>> getRecommend(@RequestParam(value = "id")long id);
}
