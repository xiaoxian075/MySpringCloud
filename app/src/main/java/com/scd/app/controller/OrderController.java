package com.scd.app.controller;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.SubmitOrderAcceptMsg;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.vo.OrderInfoVo;
import com.scd.app.pojo.vo.OrderSubmitVo;
import com.scd.app.pojo.vo.SelectOrderVo;
import com.scd.app.service.OrderService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.SubmitOrderBo;
import com.scd.sdk.util.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName OrderController
 * @Description 订单
 * @author chenjx
 * @date 2018-03-05
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private OrderService orderService;
	
	
	
	/**
	 * 订单提交
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(HttpServletRequest request) {
		OrderSubmitVo vo = null;
		try {
			SubmitOrderAcceptMsg acceptMsg = Constant.subPack(request, SubmitOrderAcceptMsg.class);
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
			
			SubmitOrderBo submitOrder = new SubmitOrderBo(session.getAccount(), acceptMsg.getOdd(), acceptMsg.getType(), acceptMsg.getAddressId(), acceptMsg.getOrderList());
			Return<OrderSubmitVo> ret = orderService.submit(submitOrder);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			vo = ret.getData();
			if (vo == null) {
				return Constant.pack(ErrorCom.NOT_EXIST);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		return Constant.pack(vo);
	}
	
	/**
	 * 分类查看订单条数
	 * @return
	 */
	@RequestMapping(value = "/selectCount", method = RequestMethod.POST)
	public String selectCount(HttpServletRequest request) {
		SelectOrderVo vo = null;
		try {
			ZeroAcceptMsg acceptMsg = Constant.subPack(request, ZeroAcceptMsg.class);
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
			
			Return<SelectOrderVo> ret = orderService.selectCount(session.getAccount());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			vo = ret.getData();
			if (vo == null) {
				return Constant.pack(ErrorCom.NOT_EXIST);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(vo);
	}
	
	/**
	 * 查看订单信息
	 * @return
	 */
	@RequestMapping(value = "/selectOrder", method = RequestMethod.POST)
	public String selectOrder(HttpServletRequest request) {
		OrderInfoVo vo = null;
		try {
			StrAcceptMsg acceptMsg = Constant.subPack(request, StrAcceptMsg.class);
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
			
			Return<OrderInfoVo> ret = orderService.selectOrder(session.getAccount(), acceptMsg.getStr());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			vo = ret.getData();
			if (vo == null) {
				return Constant.pack(ErrorCom.NOT_EXIST);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(vo);
	}
	
	/**
	 * 查看订单列表
	 * @return
	 */
	@RequestMapping(value = "/selectOrderList", method = RequestMethod.POST)
	public String selectOrderList(HttpServletRequest request) {
		List<OrderInfoVo> voList = null;
		try {
			IdPageAcceptMsg acceptMsg = Constant.subPack(request, IdPageAcceptMsg.class);
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
			
			Return<List<OrderInfoVo>> ret = orderService.selectOrderList(acceptMsg.getPage(), acceptMsg.getSize(), session.getAccount(), (int)acceptMsg.getId());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			voList = ret.getData();
			if (voList == null) {
				return Constant.pack(ErrorCom.NOT_EXIST);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(voList);
	}
	
	/**
	 * 取消订单
	 * @return
	 */
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	public String cancelOrder(HttpServletRequest request) {
		OrderInfoVo vo = null;
		try {
			StrAcceptMsg acceptMsg = Constant.subPack(request, StrAcceptMsg.class);
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
			
			Return<OrderInfoVo> ret = orderService.cancelOrder(session.getAccount(), acceptMsg.getStr());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			vo = ret.getData();
			if (vo == null) {
				return Constant.pack(ErrorCom.NOT_EXIST);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(vo);
	}
	
	/**
	 * 提醒发货
	 * @return
	 */
	@RequestMapping(value = "/reminderDelivery", method = RequestMethod.POST)
	public String reminderDelivery(HttpServletRequest request) {

		try {
			StrAcceptMsg acceptMsg = Constant.subPack(request, StrAcceptMsg.class);
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
			
			Return<Object> ret = orderService.reminderDelivery(session.getAccount(), acceptMsg.getStr());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack();
	}
	
	/**
	 * 确认收货
	 * @return
	 */
	@RequestMapping(value = "/confirmationReceipt", method = RequestMethod.POST)
	public String confirmationReceipt(HttpServletRequest request) {
		OrderInfoVo vo = null;
		try {
			StrAcceptMsg acceptMsg = Constant.subPack(request, StrAcceptMsg.class);
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
			
			Return<OrderInfoVo> ret = orderService.confirmationReceipt(session.getAccount(), acceptMsg.getStr());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			vo = ret.getData();
			if (vo == null) {
				return Constant.pack(ErrorCom.NOT_EXIST);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(vo);
	}
}
