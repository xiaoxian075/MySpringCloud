package com.scd.admin.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.admin.pojo.vo.OrderVo;
import com.scd.joggle.pojo.po.OrderPo;

public class OrderUtil {

	public static List<OrderVo> change(List<OrderPo> poList) {
		if (poList == null) {
			return null;
		}
		List<OrderVo> voList = new ArrayList<OrderVo>();
		for (OrderPo po : poList) {
			OrderVo vo = change(po);
			if (vo != null) {
				voList.add(vo);
			}
		}
		return voList;
	}

	private static OrderVo change(OrderPo po) {
		if (po == null) {
			return null;
		}
		
		return new OrderVo(
				po.getId(),
				po.getAccount(),
				po.getOdd(),
				po.getState(),
				po.getGoodsPrice(),
				po.getExpPrice(),
				po.getLastPay(),
				po.getGoodsInfo(),
				po.getAddress(),
				po.getLogistics(),
				po.getCreateTime(),
				po.getUpdateTime(),
				po.getPayTime()
				);
	}

}
