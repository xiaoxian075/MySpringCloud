package com.scd.coredb.feign;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.TrolleyService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.TrolleyPo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/trolley")
public class FTrolley {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private TrolleyService trolleyService;
	
	@RequestMapping(value = "/getList")
	Return<List<TrolleyPo>> getList(String account) {
		Return<List<TrolleyPo>> ret  = null;
		try {
			ret = trolleyService.getList(account);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
	
		return ret;
	}

	@RequestMapping(value = "/add")
	Return<TrolleyPo> add(String odd, String account, long communityId, long attrId, int num) {
		Return<TrolleyPo> ret  = null;
		try {
			ret = trolleyService.add(odd, account, communityId, attrId, num);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
	
		return ret;
	}

	@RequestMapping(value = "/editNum")
	Return<TrolleyPo> editNum(long id, String account, int num) {
		Return<TrolleyPo> ret  = null;
		try {
			ret = trolleyService.editNum(id, account, num);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
	
		return ret;
	}

	@RequestMapping(value = "/batchDel")
	Return<List<Long>> batchDel(String account, @RequestBody List<Long> idList) {
		Return<List<Long>> ret  = null;
		try {
			ret = trolleyService.batchDel(account, idList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return ret;
	}
}
