package com.scd.admin.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HShopActivity;
import com.scd.joggle.pojo.po.ShopActivityPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;


@FeignClient(value = "${feign.coredb}", fallback = HShopActivity.class)
public interface FShopActivity {

	@RequestMapping(value = "/shopActivity/list")
	Return<PageInfo<ShopActivityPo>> list(@RequestParam(value = "page")int page, @RequestParam(value = "size")int size, @RequestParam(value = "shopId")long shopId);

	@RequestMapping(value = "/shopActivity/add")
	Return<ShopActivityPo> add(@RequestParam(value = "shopId")long shopId, @RequestParam(value = "title")String title);

	
	@RequestMapping(value = "/shopActivity/edit")
	Return<ShopActivityPo> edit(@RequestParam(value = "id")long id, @RequestParam(value = "shopId")long shopId, @RequestParam(value = "title")String title);

	@RequestMapping(value = "/shopActivity/del")
	Return<Long> del(@RequestParam(value = "id")long id);

	@RequestMapping(value = "/shopActivity/batchDel")
	Return<List<Long>> batchDel(@RequestBody List<Long> idList);

}
