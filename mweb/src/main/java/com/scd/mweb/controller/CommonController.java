package com.scd.mweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.joggle.constant.ErrorCom;
import com.scd.mweb.config.CommonConfig;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.pojo.bo.RootUrlBo;
import com.scd.sdk.util.pojo.H5ZeroAcceptMsg;

@RestController
@RequestMapping(value = "/common")
public class CommonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	/**
	 * 获取URL前缀
	 */
	@RequestMapping(value = "/getRootUrl", method = RequestMethod.POST)
	public String getRootUrl(HttpServletRequest request) {
		RootUrlBo rootUrlBo = null;
		try {
			H5ZeroAcceptMsg acceptMsg = Constant.subPack(request, H5ZeroAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			String url = CommonConfig.getRootUrl();
			String html5Url = CommonConfig.getHtml5Url();
			String html5Json = CommonConfig.getHtml5Json();
			
			rootUrlBo = new RootUrlBo(url, html5Url, html5Json);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(rootUrlBo);
	}
	
}
