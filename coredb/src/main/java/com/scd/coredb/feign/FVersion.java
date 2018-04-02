package com.scd.coredb.feign;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.VersionService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.VersionPo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/version")
public class FVersion {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private VersionService versionService;
	
	@RequestMapping(value = "/getVersion")
	Return<VersionPo> getVersion(int type) {
		Return<VersionPo> ret  = null;
		try {
			ret = versionService.getVersion(type);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
	
		return ret;
	}
	
	@RequestMapping(value = "/getAll")
	Return<List<VersionPo>> getAll() {
		Return<List<VersionPo>> ret  = null;
		try {
			ret = versionService.getAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
	
		return ret;
	}

	@RequestMapping(value = "/edit")
	Return<VersionPo> edit(long id, String version, int state) {
		Return<VersionPo> ret  = null;
		try {
			ret = versionService.edit(id, version, state);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
	
		return ret;
	}
}
