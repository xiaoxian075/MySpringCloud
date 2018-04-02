package com.scd.coredb.feign;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.ShopService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/shop")
public class FShop {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ShopService shopService;
	

	@RequestMapping(value = "/getAllShop")
	public Return<List<ShopPo>> getAllShop() {
		Return<List<ShopPo>> ret  = null;
		try {
			ret = shopService.getAllShop();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
	
		return ret;
	}
	

    @RequestMapping(value = "/list")
    public Return<PageInfo<ShopPo>> list(int page, int size) {
    	Return<PageInfo<ShopPo>> ret = null;
		try {
			ret = shopService.list(page, size);
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
			ret = shopService.del(id);
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
            ret = shopService.batchDel(idList);
        } catch (Exception e) {
            logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
    }

    @RequestMapping(value = "/add")
    public Return<Object> add(String name, @RequestBody List<String> listPic) {
    	Return<Object> ret = null;
		try {
			ret = shopService.add(name, listPic);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
    }


    @RequestMapping(value = "/edit")
    public Return<Object> edit(long id, String name, @RequestBody List<String> listPic) {
    	Return<Object> ret = null;
		try {
			ret = shopService.edit(id, name, listPic);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
    }

    @RequestMapping(value = "/getShop")
    public Return<ShopPo> getShop(long shopId) {
    	Return<ShopPo> ret = null;
		try {
			ret = shopService.getShop(shopId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
    }
    

    
}
