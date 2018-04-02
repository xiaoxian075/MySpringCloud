package com.scd.mweb.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.joggle.pojo.bo.TypeIdBo;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.pojo.vo.Html5BaseVo;
import com.scd.mweb.pojo.vo.Html5Vo;
import com.scd.mweb.service.Hhtm5Service;
import com.scd.sdk.util.GsonUtil;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/html5")
public class Hhtm5Controller {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private Hhtm5Service hhtm5Service;
	
	@RequestMapping(value = "/getData", method = RequestMethod.POST)
	public String getData(HttpServletRequest request) {
		Html5BaseVo base = new Html5BaseVo();
		Html5Vo vo = null;
		try {
			String token = request.getParameter("token");
			String strType = request.getParameter("type");
			String strId = request.getParameter("id");
			if (token == null || token.length() == 0 || strType == null || strType.length() == 0 || strId == null || strId.length() == 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			long type = Long.parseLong(strType);
			long id = Long.parseLong(strId);
			if (type <= 0 || id <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Return<RichBo> ret = hhtm5Service.getHtml5Info(type, id);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			RichBo richBo = ret.getData();
			if (richBo == null) {
				return Constant.pack(ErrorCom.NOT_EXIST);
			}
			TypeIdBo typeIdBo = new TypeIdBo((int)richBo.getType(), richBo.getForeignId());
			String link = GsonUtil.toString(typeIdBo);
			vo = new Html5Vo(richBo.getData(), link);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		base.setInfo(vo);
		
		return GsonUtil.toString(base);
	}
}
