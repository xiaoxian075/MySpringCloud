package com.scd.app.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.app.pojo.vo.OrderInfoVo;
import com.scd.app.pojo.vo.OrderVo;
import com.scd.app.pojo.vo.SelectOrderVo;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.OrderTypeNumBo;
import com.scd.joggle.pojo.po.OrderInfoPo;
import com.scd.joggle.pojo.po.OrderPo;

public class OrderUtil {

	public static OrderVo change(OrderPo po) {
		if (po == null) {
			return null;
		}
		
		long lastPay = po.getLastPay();
		long curTime = System.currentTimeMillis();
		long surplusTime = lastPay - curTime;
		if (surplusTime < 0) {
			surplusTime = 0;
		}
		
		return new OrderVo(po.getId(),
				po.getOdd(),
				po.getState(),
				po.getGoodsPrice(),
				po.getExpPrice(),
				po.getGoodsInfo(),
				po.getAddress(),
				po.getLogistics(),
				surplusTime);
	}

	public static SelectOrderVo change(List<OrderTypeNumBo> orderTypeNumList) {
		if (orderTypeNumList == null) {
			return null;
		}
		
		SelectOrderVo vo = new SelectOrderVo();
		for (OrderTypeNumBo bo : orderTypeNumList) {
			int type = bo.getType();
			int num = bo.getNum();
			switch (type) {
			case Type.PENDING_PAYMENT_ORDER:
				vo.setNoPay(num);
				break;
			case Type.PENDING_SHIPMENT_ORDER:
				vo.setNoSend(num);
				break;
			case Type.PENDING_RECEIPT_ORDER:
				vo.setNoTake(num);
				break;
			case Type.TO_BE_EVALUATED_ORDER:
				vo.setNoAppraise(num);
				break;
			}
		}
		return vo;
	}

	public static List<OrderInfoVo> changeList(List<OrderInfoPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<OrderInfoVo> voList = new ArrayList<OrderInfoVo>();
		for (OrderInfoPo po : poList) {
			OrderInfoVo vo = change(po);
			if (vo != null) {
				voList.add(vo);
			}
		}
		return voList;
	}

	public static OrderInfoVo change(OrderInfoPo po) {
		if (po == null) {
			return null;
		}
		
		long lastPay = po.getLastPay();
		long surplusTime = lastPay - System.currentTimeMillis();
		if (surplusTime < 0) {
			surplusTime = 0;
		}
		
		return new OrderInfoVo(
				po.getId(),
				po.getOdd(),
				po.getTitle(),
				po.getShowPic(),
				po.getPrice(),
				po.getState(),
				po.getGoodsPrice(),
				po.getExpPrice(),
				po.getCommudityList(),
				po.getAddress(),
				po.getLogistics(),
				surplusTime,
				po.getPayTime(),
				po.getCreateTime()
				);

	}
}
