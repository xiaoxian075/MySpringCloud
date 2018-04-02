package com.scd.coredb.feign;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.PushService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.PushPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/push")
public class FPush {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private PushService pushService;
	
	@RequestMapping(value = "/list")
	public Return<PageInfo<PushPo>> list(String account, int page, int size) {
		Return<PageInfo<PushPo>> ret = null;
		try {
			ret = pushService.list(account, page, size);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}
	
	@RequestMapping(value = "/getOne")
	public Return<PushPo> getOne(long id) {
		Return<PushPo> ret = null;
		try {
			ret = pushService.getOne(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}
	
	@RequestMapping(value = "/readOne")
	public Return<PushPo> readOne(String account, long id) {
		Return<PushPo> ret = null;
		try {
			ret = pushService.readOne(account, id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}
}
