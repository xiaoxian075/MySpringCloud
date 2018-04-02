package com.scd.coredb.feign;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.ShopActivityService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopActivityPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/shopActivity")
public class FShopActivity {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ShopActivityService shopActivityService;
	
	@RequestMapping(value = "/getList")
	public Return<List<ShopActivityPo>> getList(long shopId) {
    	Return<List<ShopActivityPo>> ret = null;
		try {
			ret = shopActivityService.getList(shopId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}
	
	@RequestMapping(value = "/list")
	Return<PageInfo<ShopActivityPo>> list(int page, int size, long shopId) {
    	Return<PageInfo<ShopActivityPo>> ret = null;
		try {
			ret = shopActivityService.list(page, size, shopId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}

	@RequestMapping(value = "/add")
	Return<ShopActivityPo> add(long shopId, String title) {
    	Return<ShopActivityPo> ret = null;
		try {
			ret = shopActivityService.add(shopId, title);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}

	
	@RequestMapping(value = "/edit")
	Return<ShopActivityPo> edit(long id, long shopId, String title) {
    	Return<ShopActivityPo> ret = null;
		try {
			ret = shopActivityService.edit(id, shopId, title);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}

	@RequestMapping(value = "/del")
	Return<Long> del(long id) {
    	Return<Long> ret = null;
		try {
			ret = shopActivityService.del(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}

	@RequestMapping(value = "/batchDel")
	Return<List<Long>> batchDel(@RequestBody List<Long> idList) {
    	Return<List<Long>> ret = null;
		try {
			ret = shopActivityService.batchDel(idList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}
}
