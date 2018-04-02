package com.scd.coredb.feign;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.pojo.db.TArea;
import com.scd.coredb.service.AreaService;
import com.scd.coredb.service.ExpressService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.joggle.pojo.po.AreaPo;
import com.scd.sdk.util.pojo.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/common")
public class FCommon {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private AreaService areaService;
	
	@Resource
	private ExpressService expressService;
	
	
	@RequestMapping(value = "/getAreaChild")
    public Return<List<AreaShortPojo>> getAreaChild(long parentId) {
		Return<List<AreaShortPojo>> ret = null;
		try {
			ret = areaService.findByParentId(parentId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
    }
	

	@RequestMapping(value = "/getAllArea")
    public Return<List<AreaPo>> getAllArea() {
		List<AreaPo> poList = null;
    	try {
			List<TArea> areaList = areaService.findAllArea();
			if (areaList == null) {
				return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
			}
			
			poList = new ArrayList<AreaPo>();
	    	for (TArea tArea : areaList) {
	    		poList.add(tArea.createPo());
	    	}
	    	
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return Constant.createReturn(poList);
    }
	
	@RequestMapping(value = "/getExp")
    public Return<BigDecimal> getExp(long commodityId, long addressId) {
		BigDecimal expPrice = null;
    	try {
    		Return<BigDecimal> ret = expressService.getExp(commodityId, addressId);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			expPrice = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return Constant.createReturn(expPrice);
    }
	
	@RequestMapping(value = "/getMulExp")
	public Return<List<BigDecimal>> getMulExp(@RequestBody List<List<Long>> commodityIdList, long addressId) {
		List<BigDecimal> expList = null;
    	try {
    		Return<List<BigDecimal>> ret = expressService.getMulExp(commodityIdList, addressId);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			expList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return Constant.createReturn(expList);
	}
}
