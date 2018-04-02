package com.scd.admin.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HCommodity;
import com.scd.joggle.pojo.param.CommodityAttrParam;
import com.scd.joggle.pojo.param.CommodityParam;
import com.scd.joggle.pojo.param.CommoditySelectParam;
import com.scd.joggle.pojo.po.CommodityAttrPo;
import com.scd.joggle.pojo.po.CommodityOriPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HCommodity.class)
public interface FCommodity {

	@RequestMapping(value = "/commodity/getPage")
	Return<PageInfo<CommodityOriPo>> getPage(@RequestBody CommoditySelectParam param);

	@RequestMapping(value = "/commodity/add")
	Return<CommodityOriPo> add(@RequestBody CommodityParam param);

	@RequestMapping(value = "/commodity/edit")
	Return<CommodityOriPo> edit(@RequestBody CommodityParam param);

	@RequestMapping(value = "/commodity/del")
	Return<Long> del(@RequestParam(value = "id")long id);

	@RequestMapping(value = "/commodity/batchDel")
	Return<List<Long>> batchDel(@RequestBody List<Long> idList);

	@RequestMapping(value = "/commodity/setState")
	Return<CommodityOriPo> setState(@RequestParam(value = "id")long id, @RequestParam(value = "state")int state);

	@RequestMapping(value = "/commodity/getAttrPage")
	Return<PageInfo<CommodityAttrPo>> getAttrPage(@RequestParam(value = "commodityId")long commodityId, @RequestParam(value = "page")int page, @RequestParam(value = "size")int size);

	@RequestMapping(value = "/commodity/attrAdd")
	Return<CommodityAttrPo> attrAdd(@RequestBody CommodityAttrParam param);

	@RequestMapping(value = "/commodity/attrEdit")
	Return<CommodityAttrPo> attrEdit(@RequestBody CommodityAttrParam param);

	@RequestMapping(value = "/commodity/attrDel")
	Return<Long> attrDel(@RequestParam(value = "id") long id);

	@RequestMapping(value = "/commodity/attrBatchDel")
	Return<List<Long>> attrBatchDel(@RequestBody List<Long> idList);
}
