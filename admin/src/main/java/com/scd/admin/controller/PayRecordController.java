package com.scd.admin.controller;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.PayRecordAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.PayRecordVo;
import com.scd.admin.service.PayRecordService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.PayRecordSelectParam;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/payRecord")
public class PayRecordController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private PayRecordService payRecordService;
	

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletRequest request) {
		PageInfo<PayRecordVo> pageInfo = null;
		try {
			PayRecordAcceptMsg acceptMsg = Constant.subPack(request, PayRecordAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
		      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		      if (session == null) {
		          return Constant.pack(ErrorCom.GET_LOGIN);
		      }

			PayRecordSelectParam param = new PayRecordSelectParam(
					acceptMsg.getTradeOdd(),
					acceptMsg.getOrderOdd(),
					acceptMsg.getPayAccount(),
					acceptMsg.getReceiveAccount(),
					acceptMsg.getPayWay(),
					acceptMsg.getAct(),
					acceptMsg.getPage(),
					acceptMsg.getSize());

			Return<PageInfo<PayRecordVo>> ret = payRecordService.list(param);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			pageInfo = ret.getData();
		} catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.pack(ErrorCom.ERROR);
        }
		
		return Constant.pack(pageInfo);
	}

}
