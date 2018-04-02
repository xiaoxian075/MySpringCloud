package com.scd.coredb.pojo.util;

import com.google.gson.reflect.TypeToken;
import com.scd.coredb.pojo.db.TOrder;
import com.scd.joggle.pojo.bo.AddressBo;
import com.scd.joggle.pojo.bo.LogisticsBo;
import com.scd.joggle.pojo.bo.OrderCommodityBo;
import com.scd.joggle.pojo.po.OrderInfoPo;
import com.scd.joggle.pojo.po.OrderPo;
import com.scd.sdk.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderUtil {

	public static List<OrderInfoPo> change(List<TOrder> orderList) {
		if (orderList == null) {
			return null;
		}
		
		List<OrderInfoPo> poList = new ArrayList<OrderInfoPo>();
		for (TOrder tOrder : orderList) {
			OrderInfoPo po = change(tOrder);
			if (po != null) {
				poList.add(po);
			}
		}
		
		return poList;
	}

	public static OrderInfoPo change(TOrder tOrder) {
		List<OrderCommodityBo> goodsInfo = GsonUtil.toJson(tOrder.getGoodsInfo(), new TypeToken<List<OrderCommodityBo>>() {}.getType());
		if (goodsInfo == null || goodsInfo.size() == 0) {
			return null;
		}
		OrderCommodityBo orderCommodityBo = goodsInfo.get(0);
		
		AddressBo address = null;
		String strAddrInfo = tOrder.getAddrInfo();
		if (strAddrInfo != null && strAddrInfo.length() > 0) {
			address = GsonUtil.toJson(tOrder.getAddrInfo(), AddressBo.class);
			if (address == null) {
				return null;
			}
		}
		
		LogisticsBo logistics = null;
		String logisticsInfo = tOrder.getLogisticsInfo();
		if (logisticsInfo != null && logisticsInfo.length() > 0) {
			logistics = GsonUtil.toJson(tOrder.getLogisticsInfo(), LogisticsBo.class);
			if (logistics == null) {
				return null;
			}
		}

		return new OrderInfoPo(
				tOrder.getId(),
				tOrder.getAccount(),
				tOrder.getOdd(),
				orderCommodityBo.getTitle(),
				orderCommodityBo.getShowPic(),
				orderCommodityBo.getPrice(),
				tOrder.getState(),
				tOrder.getGoodsPrice(),
				tOrder.getExpPrice(),
				goodsInfo,
				address,
				logistics,
				tOrder.getLastPay(),
				tOrder.getPayTime(),
				tOrder.getCreateTime());
	}

	public static TOrder change(OrderPo orderPo) {
		String goodsInfo = GsonUtil.toString(orderPo.getGoodsInfo());
		if (goodsInfo == null) {
			return null;
		}
		String addressInfo = GsonUtil.toString(orderPo.getAddress());
		if (addressInfo == null) {
			return null;
		}
		
		String logisticsInfo = "";
		LogisticsBo logistics = orderPo.getLogistics();
		if (logistics != null) {
			logisticsInfo = GsonUtil.toString(logistics);
			if (logisticsInfo == null) {
				return null;
			}
		}
		
		
		return new TOrder(
				orderPo.getId(),
				orderPo.getAccount(),
				orderPo.getOdd(),
				orderPo.getState(),
				orderPo.getGoodsPrice(),
				orderPo.getExpPrice(),
				orderPo.getLastPay(),
				goodsInfo,
				addressInfo,
				logisticsInfo,
				orderPo.getCreateTime(),
				orderPo.getUpdateTime(),
				orderPo.getPayTime(),
				0);
	}

	public static List<OrderPo> changePoList(List<TOrder> orderList) {
		if (orderList == null) {
			return null;
		}
		
		List<OrderPo> poList = new ArrayList<OrderPo>();
		for (TOrder tOrder : orderList) {
			OrderPo po = changeToPo(tOrder);
			if (po != null) {
				poList.add(po);
			}
		}
		
		return poList;
	}

	private static OrderPo changeToPo(TOrder tOrder) {
		List<OrderCommodityBo> goodsInfo = GsonUtil.toJson(tOrder.getGoodsInfo(), new TypeToken<List<OrderCommodityBo>>() {}.getType());
		if (goodsInfo == null || goodsInfo.size() == 0) {
			return null;
		}
		
		AddressBo address = null;
		String strAddrInfo = tOrder.getAddrInfo();
		if (strAddrInfo != null && strAddrInfo.length() > 0) {
			address = GsonUtil.toJson(tOrder.getAddrInfo(), AddressBo.class);
			if (address == null) {
				return null;
			}
		}
		
		LogisticsBo logistics = null;
		String logisticsInfo = tOrder.getLogisticsInfo();
		if (logisticsInfo != null && logisticsInfo.length() > 0) {
			logistics = GsonUtil.toJson(tOrder.getLogisticsInfo(), LogisticsBo.class);
			if (logistics == null) {
				return null;
			}
		}

		return new OrderPo(
				tOrder.getId(),
				tOrder.getAccount(),
				tOrder.getOdd(),
				tOrder.getState(),
				tOrder.getGoodsPrice(),
				tOrder.getExpPrice(),
				tOrder.getLastPay(),
				goodsInfo,
				address,
				logistics,
				tOrder.getCreateTime(),
				tOrder.getUpdateTime(),
				tOrder.getPayTime());
	}
}
