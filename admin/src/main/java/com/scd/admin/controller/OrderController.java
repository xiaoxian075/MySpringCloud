package com.scd.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.OrderPageAcceptMsg;
import com.scd.admin.controller.accept.SendGoodsAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.OrderVo;
import com.scd.admin.service.OrderService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.OrderSelectParam;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private OrderService orderService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletRequest request) {
		PageInfo<OrderVo> pageInfo = null;
		try {
			OrderPageAcceptMsg acceptMsg = Constant.subPack(request, OrderPageAcceptMsg.class);
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
	
			OrderSelectParam param = new OrderSelectParam(
					acceptMsg.getPage(), 
					acceptMsg.getSize(),
					acceptMsg.getAccount(),
					acceptMsg.getOdd(),
					acceptMsg.getState());
			Return<PageInfo<OrderVo>> ret = orderService.list(param);
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
	
	/**
	 * 发货
	 */
	@RequestMapping(value = "/sendGoods", method = RequestMethod.POST)
	public String sendGoods(HttpServletRequest request) {
		try {
			SendGoodsAcceptMsg acceptMsg = Constant.subPack(request, SendGoodsAcceptMsg.class);
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
	
			Return<Object> ret = orderService.sendGoods(acceptMsg.getOrderOdd(), acceptMsg.getCode(), acceptMsg.getExpOdd());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack();
	}
}
