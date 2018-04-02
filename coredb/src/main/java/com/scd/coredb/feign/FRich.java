package com.scd.coredb.feign;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.RichService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/rich")
public class FRich {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private RichService richService;
	
	@RequestMapping(value = "/selectOne")
	public Return<RichBo> selectOne(long type, long foreignId) {
		Return<RichBo> ret = null;
		try {
			ret = richService.selectOne(type, foreignId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}
	
	@RequestMapping(value = "/submit")
	public Return<RichBo> submit(@RequestBody RichBo richBo) {
		Return<RichBo> ret = null;
		try {
			ret = richService.submit(richBo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}
}
