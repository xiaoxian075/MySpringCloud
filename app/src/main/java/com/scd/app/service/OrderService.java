package com.scd.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FOrder;
import com.scd.app.pojo.util.OrderUtil;
import com.scd.app.pojo.vo.OrderInfoVo;
import com.scd.app.pojo.vo.OrderSubmitVo;
import com.scd.app.pojo.vo.SelectOrderVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.OrderTypeNumBo;
import com.scd.joggle.pojo.bo.SubmitOrderBo;
import com.scd.joggle.pojo.po.OrderInfoPo;
import com.scd.sdk.util.pojo.Return;

@Service
public class OrderService {
	
	@Autowired
	private FOrder fOrder;

	public Return<OrderSubmitVo> submit(SubmitOrderBo submitOrder) {
		Return<String> ret = fOrder.submit(submitOrder);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}

		OrderSubmitVo vo = new OrderSubmitVo(ret.getData());
		
		return Constant.createReturn(vo);
	}

	public Return<SelectOrderVo> selectCount(String account) {
		Return<List<OrderTypeNumBo>> ret = fOrder.selectCount(account);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}

		SelectOrderVo vo = OrderUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(vo);
	}

	public Return<List<OrderInfoVo>> selectOrderList(int page, int size, String account, int orderState) {
		Return<List<OrderInfoPo>> ret = fOrder.selectOrderList(page, size, account, orderState);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}

		List<OrderInfoVo> voList = OrderUtil.changeList(ret.getData());
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(voList);
	}
	
	public Return<OrderInfoVo> selectOrder(String account, String odd) {
		Return<OrderInfoPo> ret = fOrder.selectOrder(account, odd);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}

		OrderInfoVo vo = OrderUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(vo);
	}

	public Return<OrderInfoVo> cancelOrder(String account, String odd) {
		Return<OrderInfoPo> ret = fOrder.cancelOrder(account, odd);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}

		OrderInfoVo vo = OrderUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(vo);
	}

	public Return<Object> reminderDelivery(String account, String odd) {
		Return<Object> ret = fOrder.reminderDelivery(account, odd);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}

		return Constant.createReturn();
	}

	public Return<OrderInfoVo> confirmationReceipt(String account, String odd) {
		Return<OrderInfoPo> ret = fOrder.confirmationReceipt(account, odd);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}

		OrderInfoVo vo = OrderUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(vo);
	}

}
