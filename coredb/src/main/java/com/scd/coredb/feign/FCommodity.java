package com.scd.coredb.feign;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.CommodityService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.CommodityAttrParam;
import com.scd.joggle.pojo.param.CommodityParam;
import com.scd.joggle.pojo.param.CommoditySelectParam;
import com.scd.joggle.pojo.po.CommodityAttrPo;
import com.scd.joggle.pojo.po.CommodityOriPo;
import com.scd.joggle.pojo.po.CommodityPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/commodity")
public class FCommodity {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private CommodityService commodityService;
	
	@RequestMapping(value = "/getOne")
	public Return<CommodityPo> getOne(long id) {
		CommodityPo po = null;
    	try {
			Return<CommodityPo> ret = commodityService.getOne(id);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			po = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return Constant.createReturn(po);
	}
	
	@RequestMapping(value = "/getPageIncludeAttr")
    public Return<List<CommodityPo>> getPageIncludeAttr(long shopId, int type, int page, int size) {
		List<CommodityPo> poList = null;
    	try {
			Return<List<CommodityPo>> ret = commodityService.getPageIncludeAttr(shopId, type, page, size);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			poList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return Constant.createReturn(poList);
	}
	
	@RequestMapping(value = "/getPage")
    public Return<PageInfo<CommodityOriPo>> getPage(@RequestBody CommoditySelectParam param) {
		Return<PageInfo<CommodityOriPo>> ret = null;
    	try {
			 ret = commodityService.getPage(param);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}
	
	@RequestMapping(value = "/add")
    public Return<CommodityOriPo> add(@RequestBody CommodityParam param) {
		Return<CommodityOriPo> ret = null;
    	try {
			ret = commodityService.add(param);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}

	@RequestMapping(value = "/edit")
    public Return<CommodityOriPo> edit(@RequestBody CommodityParam param) {
		Return<CommodityOriPo> ret = null;
    	try {
    		ret = commodityService.edit(param);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}
	
	@RequestMapping(value = "/del")
    public Return<Long> del(long id) {
		Return<Long> ret = null;
    	try {
			ret = commodityService.del(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}

	@RequestMapping(value = "/batchDel")
    public Return<List<Long>> batchDel(@RequestBody List<Long> idList) {
		Return<List<Long>> ret = null;
    	try {
    		ret = commodityService.batchDel(idList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}
	
	@RequestMapping(value = "/setState")
    public Return<CommodityOriPo> setState(long id, int state) {
		Return<CommodityOriPo> ret = null;
    	try {
    		ret = commodityService.setState(id, state);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}
	
	
	@RequestMapping(value = "/getAttrPage")
    public Return<PageInfo<CommodityAttrPo>> getAttrPage(long commodityId, int page, int size) {
		Return<PageInfo<CommodityAttrPo>> ret = null;
    	try {
			ret = commodityService.getAttrPage(commodityId, page, size);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}
	
	@RequestMapping(value = "/attrAdd")
	public Return<CommodityAttrPo> attrAdd(@RequestBody CommodityAttrParam param) {
		Return<CommodityAttrPo> ret = null;
    	try {
    		ret = commodityService.attrAdd(param);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}

	@RequestMapping(value = "/attrEdit")
	public Return<CommodityAttrPo> attrEdit(@RequestBody CommodityAttrParam param) {
		Return<CommodityAttrPo> ret = null;
    	try {
    		ret = commodityService.attrEdit(param);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}

	@RequestMapping(value = "/attrDel")
	public Return<Long> attrDel(long id) {
		Return<Long> ret = null;
    	try {
    		ret = commodityService.attrDel(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}

	@RequestMapping(value = "/attrBatchDel")
	public Return<List<Long>> attrBatchDel(@RequestBody List<Long> idList) {
		Return<List<Long>> ret = null;
    	try {
    		ret = commodityService.attrBatchDel(idList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}
	
	@RequestMapping(value = "/getAssort")
	public Return<List<CommodityPo>> getAssort(long id) {
		Return<List<CommodityPo>> ret = null;
    	try {
    		ret = commodityService.getAssort(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}
	
	@RequestMapping(value = "/getRecommend")
	public Return<List<CommodityPo>> getRecommend(long id) {
		Return<List<CommodityPo>> ret = null;
    	try {
    		ret = commodityService.getRecommend(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
	}
	
	
}
