package com.scd.coredb.feign;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.RemindService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.RemindPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/remind")
public class FRemind {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private RemindService remindService;

	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	Return<PageInfo<RemindPo>> getList(int page, int size, int state) {
		Return<PageInfo<RemindPo>> ret = null;
		try {
			ret = remindService.getPage(page, size, state);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}
	
	@RequestMapping(value = "/ignore", method = RequestMethod.POST)
	Return<Object> ignore(long id) {
		Return<Object> ret = null;
		try {
			ret = remindService.ignore(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}

}
