package com.scd.coredb.feign;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.OrderService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.OrderTypeNumBo;
import com.scd.joggle.pojo.bo.SubmitOrderBo;
import com.scd.joggle.pojo.param.OrderSelectParam;
import com.scd.joggle.pojo.po.OrderInfoPo;
import com.scd.joggle.pojo.po.OrderPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/order")
public class FOrder {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private OrderService orderService;
	
	@RequestMapping(value = "/submit")
	public Return<String> submit(@RequestBody SubmitOrderBo submitOrderBo) {

		String odd = null;
    	try {
			Return<String> ret = orderService.submit(submitOrderBo);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			odd = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return Constant.createReturn(odd);
	}


//	@RequestMapping(value = "/getOrderInfo")
//	public Return<PayPo> getOrderInfo(String odd) {
//		if (odd == null) {
//			return Constant.createReturn(ErrorCom.PARSE_ERROR);
//		}
//		PayPo payPo = null;
//		try {
//			payPo = orderService.findByOdd(odd);
//			if (payPo == null) {
//				return Constant.createReturn(ErrorCom.NOT_ORDER_INFO);
//			}
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			return Constant.createReturn(ErrorCom.ERROR);
//		}
//
//		return  Constant.createReturn(payPo);
//	}
	
	@RequestMapping(value = "/selectCount")
	public Return<List<OrderTypeNumBo>> selectCount(String account) {
		Return<List<OrderTypeNumBo>> ret = null;
		try {
			ret = orderService.selectCount(account);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}

	@RequestMapping(value = "/selectOrderList")
	public Return<List<OrderInfoPo>> selectOrderList(int page, int size, String account, int orderState) {
		Return<List<OrderInfoPo>> ret = null;
		try {
			ret = orderService.selectOrderList(page, size, account, orderState);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}
	
	@RequestMapping(value = "/selectOrder")
	public Return<OrderInfoPo> selectOrder(String account, String odd) {
		Return<OrderInfoPo> ret = null;
		try {
			ret = orderService.selectOrder(account, odd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}

	@RequestMapping(value = "/cancelOrder")
	public Return<OrderInfoPo> cancelOrder(String account, String odd) {
		Return<OrderInfoPo> ret = null;
		try {
			ret = orderService.cancelOrder(account, odd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}
	
	@RequestMapping(value = "/reminderDelivery")
	public Return<Object> reminderDelivery(String account, String odd) {
		Return<Object> ret = null;
		try {
			ret = orderService.reminderDelivery(account, odd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}

	@RequestMapping(value = "/confirmationReceipt")
	public Return<OrderInfoPo> confirmationReceipt(String account, String odd) {
		Return<OrderInfoPo> ret = null;
		try {
			ret = orderService.confirmationReceipt(account, odd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}
	

	@RequestMapping(value = "/getPage")
	public Return<PageInfo<OrderPo>> getPage(@RequestBody OrderSelectParam param) {
		Return<PageInfo<OrderPo>> ret = null;
		try {
			ret = orderService.getPage(param);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}

	@RequestMapping(value = "/sendGoods", method = RequestMethod.POST)
	public Return<Object> sendGoods(String orderOdd, String code, String expOdd) {
		Return<Object> ret = null;
		try {
			ret = orderService.sendGoods(orderOdd, code, expOdd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}

		return ret;
	}

	@RequestMapping(value = "/dealEvaluatedByOdd")
	public Return<OrderInfoPo> dealEvaluated(String odd) {

		Return<OrderInfoPo>ret = null;
		try {
			ret = orderService.dealEvaluated(odd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return ret;
	}

	@RequestMapping(value = "/dealEvaluatedById")
	public Return<OrderInfoPo> dealEvaluatedById(long id) {

		Return<OrderInfoPo>ret = null;
		try {
			ret = orderService.dealEvaluated(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return ret;
	}
}
