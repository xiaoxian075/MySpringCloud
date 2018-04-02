package com.scd.coredb.pojo.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.scd.coredb.pojo.db.TCommodity;
import com.scd.coredb.pojo.db.TCommodityAttr;
import com.scd.coredb.pojo.db.TOrder;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.AddressBo;
import com.scd.joggle.pojo.bo.OrderCommodityBo;
import com.scd.sdk.util.GsonUtil;
import com.scd.sdk.util.RandomUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildOrderBo {
	private String account;
	private long shopId;
	private String shopName;
	private AddressBo address;
	private BigDecimal commodityExp;
	private Map<Long, TCommodity> commodityMap;
	private Map<Long, TCommodityAttr> commodityAttrMap;
	private List<OrderCommodityBo> orderCommodityList;
	private BigDecimal goodsPrice;
	
	
	public ChildOrderBo(String account, long shopId, String shopName, AddressBo address) {
		this.account = account;
		this.shopId = shopId;
		this.shopName = shopName;
		this.address = address;
		this.commodityExp = BigDecimal.ZERO;
		this.commodityMap = new HashMap<Long, TCommodity>();
		this.commodityAttrMap = new HashMap<Long, TCommodityAttr>();
		this.orderCommodityList = new ArrayList<OrderCommodityBo>();
		this.goodsPrice = BigDecimal.ZERO;
	}


	public boolean add(TCommodity tCommodity, TCommodityAttr tCommodityAttr, OrderCommodityBo bo, BigDecimal commodityExp) {
		if (tCommodity == null || tCommodityAttr == null || bo == null || commodityExp == null) {
			return false;
		}
		
		long num = bo.getNum();
		long curTime = System.currentTimeMillis();
		
		long commodityId = tCommodity.getId();
		TCommodity hasTCommodity = commodityMap.get(commodityId);
		if (hasTCommodity == null) {
			hasTCommodity = tCommodity;
			commodityMap.put(commodityId, hasTCommodity);
		}
		// 加销售量
		hasTCommodity.setAllSaleNum(tCommodity.getAllSaleNum() + num);
		hasTCommodity.setMonthSaleNum(tCommodity.getMonthSaleNum() + num);
		hasTCommodity.setUpdateTime(curTime);
		
		
		long commodityAttrId = tCommodityAttr.getId();
		TCommodityAttr hasTCommodityAttr = commodityAttrMap.get(commodityAttrId);
		if (hasTCommodityAttr == null) {
			hasTCommodityAttr = tCommodityAttr;
			commodityAttrMap.put(commodityAttrId, hasTCommodityAttr);
		}
		// 减库存
		if (hasTCommodityAttr.getStockNum() < num) {
			return false;
		}
		hasTCommodityAttr.setStockNum(hasTCommodityAttr.getStockNum() - num);
		hasTCommodity.setUpdateTime(curTime);
		
		
		// 加购买的商品详情
		orderCommodityList.add(bo);
		
		// 添加到总价格中
		BigDecimal _price = bo.getPrice().multiply(new BigDecimal(num));
		goodsPrice = goodsPrice.add(_price);
		
		if (commodityExp != null && this.commodityExp.compareTo(commodityExp) < 0) {
			this.commodityExp = commodityExp;
		}
		
		return true;
	}
	
	public List<TCommodity> getCommodity() {
		List<TCommodity> list = new ArrayList<TCommodity>();
		for (Entry<Long, TCommodity> entry : commodityMap.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
	
	public List<TCommodityAttr> getCommodityAttr() {
		List<TCommodityAttr> list = new ArrayList<TCommodityAttr>();
		for (Entry<Long, TCommodityAttr> entry : commodityAttrMap.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}
	
	public TOrder getOrder(String odd) {
		long curTime = System.currentTimeMillis();
		
		//String odd = RandomUtil.getOdd();
		if (odd == null) {
			odd = RandomUtil.getOdd();
			if (odd == null) {
				return null;
			}
		}
		
		String addressInfo = GsonUtil.toString(address);
		if (addressInfo == null) {
			return null;
		}
		
		//List<OrderCommodityBo> goodsList = new ArrayList<OrderCommodityBo>();
		String goodsInfo = GsonUtil.toString(orderCommodityList);
		if (goodsInfo == null) {
			return null;
		}
		
		return new TOrder(
							0,
							account,
							odd,
							Type.PENDING_PAYMENT_ORDER,
							goodsPrice,
							commodityExp,
							curTime + 24*60*60*100,
							goodsInfo,
							addressInfo,
							"",
							curTime,
							curTime,
							0,
							0);
	}
	

}
