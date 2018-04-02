package com.scd.mweb.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.joggle.constant.ErrorCom;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.pojo.vo.IdNameVo;
import com.scd.mweb.service.ShopService;
import com.scd.sdk.util.pojo.H5IdAcceptMsg;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/shop")
public class ShopController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ShopService shopService;
	
	/**
	 * 获取店铺活动列表
	 * @return
	 */
	@RequestMapping(value = "/getActivityList", method = RequestMethod.POST)
	public String getActivityList(HttpServletRequest request) {
		List<IdNameVo> voList = null;
		try {
			H5IdAcceptMsg acceptMsg = Constant.subPack(request, H5IdAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
	
			Return<List<IdNameVo>> ret = shopService.getActivityList(acceptMsg.getId());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			voList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(voList);
	}
}
