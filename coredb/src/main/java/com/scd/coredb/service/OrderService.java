package com.scd.coredb.service;

import com.google.gson.reflect.TypeToken;
import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.*;
import com.scd.coredb.pojo.bo.ChildOrderBo;
import com.scd.coredb.pojo.bo.OrderPayBo;
import com.scd.coredb.pojo.db.*;
import com.scd.coredb.pojo.util.OrderUtil;
import com.scd.coredb.third.ThirdYft;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.*;
import com.scd.joggle.pojo.param.OrderSelectParam;
import com.scd.joggle.pojo.po.OrderInfoPo;
import com.scd.joggle.pojo.po.OrderPo;
import com.scd.sdk.util.GsonUtil;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private CommodityDao commodityDao;
	
	@Autowired
	private CommodityAttrDao commodityAttrDao;
	
	@Autowired
	private ShopDao shopDao;
		
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private AccountInfoDao accountInfoDao;
	
	@Resource
	private ExpressService expressService;
	
	@Autowired
	private AccountBalanceDao accountBalanceDao;
	
	@Autowired
	private RemindDao remindDao;
	
	@Resource
	private TrolleyService trolleyService;
	
	@Autowired
	private OrderMergePayDao orderMergePayDao;
	
	@Autowired
	private Kd100Dao kd100Dao;
	
	/**
	 * 支付成功后调用此接口更新订单状态
	 * @param odd
	 * @return
	 */
	public boolean dealPay(String odd, int type) {
		long curTime = System.currentTimeMillis();
		TOrder tOrder = orderDao.findByOdd(odd);
		if (tOrder != null) {
			if (Type.PENDING_PAYMENT_ORDER != tOrder.getState()) {
				return false;
			}
			BigDecimal amount = tOrder.getGoodsPrice().add(tOrder.getExpPrice());
			long integral = ThirdYft.getInstance().caclIntegral(amount);
			if (integral < 0) {
				return false;
			}
			tOrder.setState(Type.PENDING_SHIPMENT_ORDER);
			tOrder.setIntegral(integral);
			tOrder.setPayTime(curTime);
			tOrder.setUpdateTime(curTime);
			TOrder newTOrder = orderDao.save(tOrder);
			if (newTOrder == null) {
				return false;
			}
		} else {
			//查找包含多笔订单
			TOrderMergePay tOrderMergePay = orderMergePayDao.findByOdd(odd);
			if(tOrderMergePay == null){
				return false;
			} 

			String strOdd = tOrderMergePay.getChildOdd();
			List<String> childOdd = GsonUtil.toJson(strOdd, new TypeToken<List<String>>() {}.getType());
			if (childOdd == null) {
				return false;
			}
			List<TOrder> tList = new ArrayList<TOrder>();
			for (String _odd : childOdd) {	// 检查所有的订单存在且状态都为可支付状态
				TOrder _tOrder = orderDao.findByOdd(_odd);
				if (_tOrder == null) {
					return false;
				}
				if (_tOrder.getState() != Type.YFT_ASYN_NOTICE_NOPAY) {
					return false;
				}
				
				BigDecimal amount = _tOrder.getGoodsPrice().add(_tOrder.getExpPrice());
				long integral = ThirdYft.getInstance().caclIntegral(amount);
				if (integral < 0) {
					return false;
				}
				_tOrder.setState(Type.PENDING_SHIPMENT_ORDER);
				_tOrder.setIntegral(integral);
				_tOrder.setPayTime(curTime);
				_tOrder.setUpdateTime(curTime);
				tList.add(_tOrder);
			}
			
			for (TOrder _tOrder : tList) {
				TOrder newTOrder = orderDao.save(_tOrder);
				if (newTOrder == null) {
					return false;
				}
			}
		}
		
		return true;
	}
	

//	/**
//	 * 支付成功后调用此接口更新订单状态
//	 * @param odd
//	 * @return
//	 */
//	public Return<OrderInfoPo> dealPay(String odd) {
//		TOrder tOrder = orderDao.findByOdd(odd);
//		if (tOrder == null) {
//			return Constant.createReturn(ErrorCom.NOT_EXIST);
//		}
//		if (Type.PENDING_PAYMENT_ORDER != tOrder.getState()) {
//			return Constant.createReturn(ErrorCom.STATE_FAIL);
//		}
//		return changeState(tOrder, Type.PENDING_SHIPMENT_ORDER);
//	}
//	/**
//	 * 支付成功后调用此接口更新订单状态
//	 * @param odd
//	 * @return
//	 */
//	public Return<OrderInfoPo> dealPay(long id) {
//		TOrder tOrder = orderDao.findOne(id);
//		if (tOrder == null) {
//			return Constant.createReturn(ErrorCom.NOT_EXIST);
//		}
//		if (Type.PENDING_PAYMENT_ORDER != tOrder.getState()) {
//			return Constant.createReturn(ErrorCom.STATE_FAIL);
//		}
//		return changeState(tOrder, Type.PENDING_SHIPMENT_ORDER);
//	}
	
	/**
	 * 评价成功后调用此接口更新订单状态
	 * @param odd
	 * @return
	 */
	public Return<OrderInfoPo> dealEvaluated(String odd) {
		TOrder tOrder = orderDao.findByOdd(odd);
		if (tOrder == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		if (Type.TO_BE_EVALUATED_ORDER != tOrder.getState()) {
			return Constant.createReturn(ErrorCom.STATE_FAIL);
		}
		return changeState(tOrder, Type.COMPLETED_ORDER);
	}
	/**
	 * 评价成功后调用此接口更新订单状态
	 * @param odd
	 * @return
	 */
	public Return<OrderInfoPo> dealEvaluated(long id) {
		TOrder tOrder = orderDao.findOne(id);
		if (tOrder == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		if (Type.TO_BE_EVALUATED_ORDER != tOrder.getState()) {
			return Constant.createReturn(ErrorCom.STATE_FAIL);
		}
		return changeState(tOrder, Type.COMPLETED_ORDER);
	}
	
	private synchronized Return<OrderInfoPo> changeState(TOrder tOrder, int state) {
		
		tOrder.setState(state);
		tOrder.setUpdateTime(System.currentTimeMillis());
		TOrder newTOrder = orderDao.save(tOrder);
		if (newTOrder == null) {
			return Constant.createReturn(ErrorCom.UPDATE_ERROR);
		}
		
		OrderInfoPo po = OrderUtil.change(newTOrder);
		return Constant.createReturn(po);
	}
	
	public synchronized Return<String> submit(SubmitOrderBo submitOrder) {
		String account = submitOrder.getAccount();
		String odd = submitOrder.getOdd();
		int type = submitOrder.getType();
		long addressId = submitOrder.getAddressId();
		List<OrderGoodsBo> goodsBoList = submitOrder.getGoodsList();
		if (account == null || account.length() == 0 ||
				odd == null || odd.length() == 0 ||
				!Type.checkOrderSubmitType(type) || 
				addressId <= 0 ||
				goodsBoList == null || goodsBoList.size() == 0) {
			return Constant.createReturn(ErrorCom.PARSE_ERROR);
		}
		
		// 防止重复提交
		TOrder oldTOrder = orderDao.findByOdd(odd);
		if (oldTOrder != null) {
			return Constant.createReturn(ErrorCom.REPEATED_SUBMIT);
		}
		TOrderMergePay tOrderMergePay = orderMergePayDao.findByOdd(odd);
		if (tOrderMergePay != null) {
			return Constant.createReturn(ErrorCom.REPEATED_SUBMIT);
		}
		
		// 获取账号信息
		TAccountInfo tAccountInfo = accountInfoDao.findByAccount(account);
		if (tAccountInfo == null) {
			return Constant.createReturn(ErrorCom.ACCOUNT_NOT_EXIST);
		}
		
		// 获取地址信息
		TAddress tAddress = addressDao.findOne(addressId);
		if (tAddress == null) {
			return Constant.createReturn(ErrorCom.ADDRESS_NOT_EXIST);
		}
		AddressBo address = tAddress.createBo();
		

		Map<Long, TShop> mapShop = new HashMap<Long, TShop>();
		Map<Long, TCommodity> mapCommodity = new HashMap<Long, TCommodity>();
		Map<String, TCommodityAttr> mapCommodityAttr = new HashMap<String, TCommodityAttr>();
		Map<String, OrderCommodityBo> mapGoodsList = new HashMap<String, OrderCommodityBo>();
		for (OrderGoodsBo orderGoods : goodsBoList) {
			long commodityId = orderGoods.getCommunityId();
			long attrId = orderGoods.getAttrId();
			int num = orderGoods.getNum();
			String mergePrimary = commodityId + "#" + attrId;
			
			// 商品
			TCommodity tCommodity = mapCommodity.get(commodityId);
			if (tCommodity == null) {
				tCommodity = commodityDao.findOne(commodityId);
				if (tCommodity == null) {
					return Constant.createReturn(ErrorCom.COMMODITY_NOT_EXIST);
				}
				mapCommodity.put(tCommodity.getId(), tCommodity);
			}
			
			// 店铺
			long shopId = tCommodity.getShopId();
			TShop tShop = mapShop.get(shopId);
			if (tShop == null) {
				tShop = shopDao.findOne(shopId);
				if (tShop == null) {
					return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
				}
				mapShop.put(shopId, tShop);
			}
			
			
			// 属性
			TCommodityAttr tCommodityAttr = mapCommodityAttr.get(mergePrimary);
			if (tCommodityAttr == null) {
				Specification<TCommodityAttr> spec = new Specification<TCommodityAttr>() {
		    		@Override
		    		public Predicate toPredicate(Root<TCommodityAttr> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
			
		    			List<Predicate> predicates = new ArrayList<Predicate>();
		    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.COMMODITY_ID).as(Long.class), commodityId));
		    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.ATTR_ID).as(Long.class), attrId));
		    			
		    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		    		}
				};
				tCommodityAttr = commodityAttrDao.findOne(spec);
				if (tCommodityAttr == null) {
					return Constant.createReturn(ErrorCom.COMMODITY_ATTR_NOT_EXIST);
				}
				
				mapCommodityAttr.put(mergePrimary, tCommodityAttr);
			}
			
			//子订单列表
			OrderCommodityBo orderCommodityBo = mapGoodsList.get(mergePrimary);
			if (orderCommodityBo == null) {
				orderCommodityBo = new OrderCommodityBo(
						tCommodity.getId(), 
						tCommodity.getShortTitle(),
						tCommodity.getShowPic(),
						tCommodityAttr.getAttrId(),
						tCommodityAttr.getAttrName(),
						tShop.getId(),
						tShop.getName(),
						tCommodityAttr.getPrice(),
						0);
				mapGoodsList.put(mergePrimary, orderCommodityBo);
			}
			orderCommodityBo.setNum(orderCommodityBo.getNum() + num);
		}
		
		Map<Long, ChildOrderBo> mapChild = new HashMap<Long, ChildOrderBo>();
		for (Entry<String, OrderCommodityBo> entry : mapGoodsList.entrySet()) {
			String primary = entry.getKey();
			OrderCommodityBo bo = entry.getValue();
			if (bo == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}
			TCommodity tCommodity = mapCommodity.get(bo.getCommudityId());
			if (tCommodity == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}
			TShop tShop = mapShop.get(tCommodity.getShopId());
			if (tShop == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}
			TCommodityAttr tCommodityAttr = mapCommodityAttr.get(primary);
			if (tCommodityAttr == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}

			long shopId = tShop.getId();
			ChildOrderBo childOrderBo = mapChild.get(shopId);
			if (childOrderBo == null) {
				String shopName = tShop.getName();
				childOrderBo = new ChildOrderBo(account, shopId, shopName, address);
				mapChild.put(shopId, childOrderBo);
			}
			
			BigDecimal commodityExp = expressService.cacuFreight(address.getAddrId(), tCommodity.getAddrId());
			
			if (!childOrderBo.add(tCommodity, tCommodityAttr, bo, commodityExp)) {
				return Constant.createReturn(ErrorCom.COMMODITY_ATTR_SOCK_NOT_ENOUGN);
			}
		}
		
		// 以下为真正保存
		submitSave(account, odd, mapChild);
		
		if (type == Type.ORDER_SUBMIT_MULTI) {
			batchDelTrolley(account, mapChild);
		}
		
		return Constant.createReturn(odd);
	}
	
	private void batchDelTrolley(String account, Map<Long, ChildOrderBo> mapChild) {
		try {
			List<Long> commodityIds = new ArrayList<Long>();
			for (Entry<Long, ChildOrderBo> entry : mapChild.entrySet()) {
				ChildOrderBo bo = entry.getValue();
				List<TCommodity> commodityList = bo.getCommodity();
				for (TCommodity tCommodity : commodityList) {
					commodityIds.add(tCommodity.getId());
				}
			}
			trolleyService.batchDelByComodity(account, commodityIds);
		} catch(Exception e) {
			
		}
		
	}
	
	
	@Transactional
	public void submitSave(String account, String odd, Map<Long, ChildOrderBo> mapChild) {
		String _odd = null;
		if (mapChild.size() == 1) {
			_odd = odd;
		}
		BigDecimal amount = BigDecimal.ZERO;
		List<String> oddList = new ArrayList<String>();
		for (Entry<Long, ChildOrderBo> entry : mapChild.entrySet()) {
			TOrder tOrder = _dealOneOrder(entry.getValue(), _odd);
			if (tOrder == null) {
				throw new RuntimeException();
			}
			oddList.add(tOrder.getOdd());
			amount = amount.add(tOrder.getGoodsPrice());
			amount = amount.add(tOrder.getExpPrice());
		}
		
		
		if (_odd == null) {	// 拆单

			String strOdd = GsonUtil.toString(oddList);
			if (strOdd == null) {
				throw new RuntimeException();
			}
//			returnOdd = RandomUtil.getOdd();
//			if (returnOdd == null) {
//				throw new RuntimeException();
//			}
			
			long curTime = System.currentTimeMillis();
			TOrderMergePay tOrderMergePay = new TOrderMergePay(
					0L,
					account,
					odd,
					strOdd,
					amount,
					Type.ORDER_MERGE_REQUEST,
					curTime,
					curTime
					);
			TOrderMergePay newTOrderMergePay = orderMergePayDao.save(tOrderMergePay);
			if (newTOrderMergePay == null) {
				throw new RuntimeException();
			}
		}
	}
	
	private TOrder _dealOneOrder(ChildOrderBo bo, String odd) {
		List<TCommodity> commodityList = bo.getCommodity();
		if (commodityList == null) {
			throw new RuntimeException();
		}
		for (TCommodity tCommodity : commodityList) {
			if (commodityDao.save(tCommodity) == null) {
				throw new RuntimeException();
			}
		}
		
		List<TCommodityAttr> commodityAttr = bo.getCommodityAttr();
		if (commodityAttr == null) {
			throw new RuntimeException();
		}
		for (TCommodityAttr tCommodityAttr : commodityAttr) {
			if (commodityAttrDao.save(tCommodityAttr) == null) {
				throw new RuntimeException();
			}
		}
		
		TOrder tOrder = bo.getOrder(odd);
		if (tOrder == null) {
			throw new RuntimeException();
		}
		TOrder newTOrder = orderDao.save(tOrder);
		if (newTOrder == null) {
			throw new RuntimeException();
		}
		
		return newTOrder;
	}

	/**
	 * 根据订单号计算总金额
	 * @param odd
	 * @return
	 */
	public OrderPayBo caclAmount(String odd) {
		//查找单笔订单
		TOrder tOrder = orderDao.findByOdd(odd);
		if (tOrder == null) {
			//查找包含多笔订单
			TOrderMergePay tOrderMergePay = orderMergePayDao.findByOdd(odd);
			if(tOrderMergePay == null || tOrderMergePay.getState() != Type.ORDER_MERGE_REQUEST){
				return null;
			} else {
				String strOdd = tOrderMergePay.getChildOdd();
				List<String> childOdd = GsonUtil.toJson(strOdd, new TypeToken<List<String>>() {}.getType());
				for (String _odd : childOdd) {	// 检查所有的订单存在且状态都为可支付状态
					TOrder _tOrder = orderDao.findByOdd(_odd);
					if (_tOrder == null) {
						return null;
					}
					if (_tOrder.getState() != Type.YFT_ASYN_NOTICE_NOPAY) {
						return null;
					}
				}
				return new OrderPayBo(Type.PAY_ORDER_TYPE_MORE, tOrderMergePay.getAmount());
			}
		} else {
			if (tOrder.getState() != Type.YFT_ASYN_NOTICE_NOPAY) {
				return null;
			}
			BigDecimal goodsAmount = tOrder.getGoodsPrice();
			if (goodsAmount == null || goodsAmount.compareTo(BigDecimal.ZERO) < 0) {
				return null;
			}
			BigDecimal expAmount = tOrder.getExpPrice();
			if (expAmount == null || expAmount.compareTo(BigDecimal.ZERO) < 0) {
				return null;
			}
			BigDecimal amount = goodsAmount.add(expAmount);
			return new OrderPayBo(Type.PAY_ORDER_TYPE_ONE, amount);
		}
	}

	public Return<List<OrderTypeNumBo>> selectCount(String account) {
		List<OrderTypeNumBo> orderTypeNumBoList = new ArrayList<OrderTypeNumBo>();
		
		Specification<TOrder> spec = new Specification<TOrder>() {
    		@Override
    		public Predicate toPredicate(Root<TOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TOrder.ACCOUNT).as(String.class), account));
    			predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(TOrder.STATE).as(Integer.class), Type.PENDING_PAYMENT_ORDER));
    			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(TOrder.STATE).as(Integer.class), Type.TO_BE_EVALUATED_ORDER));
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		List<TOrder> orderList = orderDao.findAll(spec);
		
		Map<Integer, OrderTypeNumBo> mapInfo = new HashMap<Integer, OrderTypeNumBo>();
		if (orderList != null) {
			for (TOrder tOrder : orderList) {
				OrderTypeNumBo bo = mapInfo.get(tOrder.getState());
				if (bo == null) {
					bo = new OrderTypeNumBo(tOrder.getState(), 0);
					mapInfo.put(tOrder.getState(), bo);
				}
				bo.setNum(bo.getNum() + 1);
			}
		}
		
		for (Entry<Integer, OrderTypeNumBo> entry : mapInfo.entrySet()) {
			orderTypeNumBoList.add(entry.getValue());
		}
		return Constant.createReturn(orderTypeNumBoList);
	}

	public Return<OrderInfoPo> selectOrder(String account, String odd) {
		TOrder tOrder = orderDao.findByOdd(odd);
		if (tOrder == null) {
			return Constant.createReturn(ErrorCom.ORDER_NOT_EXIST);
		}
		OrderInfoPo po = OrderUtil.change(tOrder);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(po);
	}

	public Return<List<OrderInfoPo>> selectOrderList(int page, int size, String account, int orderState) {
		Specification<TOrder> spec = new Specification<TOrder>() {
    		@Override
    		public Predicate toPredicate(Root<TOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TOrder.ACCOUNT).as(String.class), account));
    			if (orderState > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TOrder.STATE).as(Integer.class), orderState));
    			}
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		Order order = new Order(Direction.DESC, TOrder.CREATE_TIME);
		Pageable pageable = new PageRequest(page, size, new Sort(order));
		Page<TOrder> orderPage = orderDao.findAll(spec, pageable);
		
		List<TOrder> orderList = orderPage.getContent(); 
		List<OrderInfoPo> poList = OrderUtil.change(orderList);
		if (poList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(poList);
	}

	public Return<OrderInfoPo> cancelOrder(String account, String odd) {
		TOrder tOrder = orderDao.findByOdd(odd);
		if (tOrder == null) {
			return Constant.createReturn(ErrorCom.ORDER_NOT_EXIST);
		}
		
		// 如果不是待付款订单，不能取消
		if (tOrder.getState() != Type.PENDING_PAYMENT_ORDER) {
			return Constant.createReturn(ErrorCom.ORDER_STATE_ERROR);
		}
		
		tOrder.setState(Type.CANCEL_ORDER);
		tOrder.setUpdateTime(System.currentTimeMillis());
		TOrder newTOrder = orderDao.save(tOrder);
		if (newTOrder == null) {
			return Constant.createReturn(ErrorCom.UPDATE_ERROR);
		}
		
		OrderInfoPo po = OrderUtil.change(newTOrder);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(po);
	}

	// 提醒发货功能暂末实现
	public Return<Object> reminderDelivery(String account, String odd) {
		TOrder tOrder = orderDao.findByOdd(odd);
		if (tOrder == null) {
			return Constant.createReturn(ErrorCom.ORDER_NOT_EXIST);
		}
		
		// 如果不是待付款订单，不能提醒发货
		if (tOrder.getState() != Type.PENDING_SHIPMENT_ORDER) {
			return Constant.createReturn(ErrorCom.ORDER_STATE_ERROR);
		}
		
		long curTime = System.currentTimeMillis();
		TRemind tRemind = new TRemind(
				0L,
				tOrder.getAccount(),
				tOrder.getOdd(),
				tOrder.getState(),
				tOrder.getGoodsPrice(),
				tOrder.getExpPrice(),
				tOrder.getAddrInfo(),
				tOrder.getCreateTime(),
				Type.REMIND_NOT_DEAL,
				curTime,
				curTime
				);
		TRemind newTRemind = remindDao.save(tRemind);
		if (newTRemind == null) {
			return Constant.createReturn(ErrorCom.SAVE_ERROR);
		}
		return Constant.createReturn();
	}


	// 确认订单
	@Transactional
	public Return<OrderInfoPo> confirmationReceipt(String account, String odd) {
		TOrder tOrder = orderDao.findByOdd(odd);
		if (tOrder == null) {
			return Constant.createReturn(ErrorCom.ORDER_NOT_EXIST);
		}
		
		// 如果不是待收货订单，不能确认收货
		if (tOrder.getState() != Type.PENDING_RECEIPT_ORDER) {
			return Constant.createReturn(ErrorCom.ORDER_STATE_ERROR);
		}
		
		TAccountBalance tAccountBalance = accountBalanceDao.findByAccount(account);
		if (tAccountBalance == null) {
			return Constant.createReturn(ErrorCom.ACCOUNT_NOT_EXIST);
		}
		
		long integral = tOrder.getIntegral();
		if (integral < 0) {
			return Constant.createReturn(ErrorCom.ZHIBEI_NOT_ERROR);
		}
		
		long curTime = System.currentTimeMillis();
		tOrder.setState(Type.TO_BE_EVALUATED_ORDER);
		tOrder.setUpdateTime(curTime);
		TOrder newTOrder = orderDao.save(tOrder);
		if (newTOrder == null) {
			throw new RuntimeException();
		}
		
		long yunBalance = tAccountBalance.getYunBalance();
		yunBalance += integral;
		tAccountBalance.setYunBalance(yunBalance);
		tAccountBalance.setUpdateTime(curTime);
		TAccountBalance newTAccountBalance = accountBalanceDao.save(tAccountBalance);
		if (newTAccountBalance == null) {
			throw new RuntimeException();
		}
		
		OrderInfoPo po = OrderUtil.change(newTOrder);
		if (po == null) {
			//return Constant.createReturn(ErrorCom.CHANGE_ERROR);
			throw new RuntimeException();
		}

		return Constant.createReturn(po);
	}



	public Return<PageInfo<OrderPo>> getPage(OrderSelectParam param) {
		int page = param.getPage();
		int size = param.getSize();
		Specification<TOrder> spec = new Specification<TOrder>() {
    		@Override
    		public Predicate toPredicate(Root<TOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			String account = param.getAccount();
    			if (account != null && account.length() > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TOrder.ACCOUNT).as(String.class), account));
    			}
    			String odd = param.getOdd();
    			if (odd != null && odd.length() > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TOrder.ODD).as(String.class), odd));
    			}
    			int state = param.getState();
    			if (state > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TOrder.STATE).as(Integer.class), state));
    			}
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		Order order = new Order(Direction.DESC, TOrder.CREATE_TIME);
		Pageable pageable = new PageRequest(page, size, new Sort(order));
		Page<TOrder> orderPage = orderDao.findAll(spec, pageable);
		
		List<TOrder> orderList = orderPage.getContent();
		List<OrderPo> poList = OrderUtil.changePoList(orderList);
		if (poList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		page = orderPage.getNumber() + 1;
		long total = orderPage.getTotalElements();
		
		PageInfo<OrderPo> kdPage = new PageInfo<OrderPo>(page, total, poList);
		
		
		return Constant.createReturn(kdPage);
	}
	

	public synchronized Return<Object> sendGoods(String orderOdd, String code, String expOdd) {
		TOrder tOrder = orderDao.findByOdd(orderOdd);
		if (tOrder == null) {
			return Constant.createReturn(ErrorCom.ORDER_NOT_EXIST);
		}
		
		// 如果不是待发货订单，不能发货
		if (tOrder.getState() != Type.PENDING_SHIPMENT_ORDER) {
			return Constant.createReturn(ErrorCom.ORDER_STATE_ERROR);
		}

		TKd100 tKd100 = kd100Dao.findByCode(code);
		if (tKd100 == null) {
			return Constant.createReturn(ErrorCom.KD100_NOT_EXIST);
		}
		LogisticsBo logistics = new LogisticsBo(code, tKd100.getName(), expOdd);
		String strLogistics = GsonUtil.toString(logistics);
		if (strLogistics == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		tOrder.setLogisticsInfo(strLogistics);
		tOrder.setState(Type.PENDING_RECEIPT_ORDER);
		tOrder.setUpdateTime(System.currentTimeMillis());
		TOrder newTOrder = orderDao.save(tOrder);
		if (newTOrder == null) {
			return Constant.createReturn(ErrorCom.UPDATE_ERROR);
		}
		
		return Constant.createReturn();
	}
	

//	public synchronized Return<OrderInfoPo> submit(SubmitOrderBo submitOrder) {
//		String account = submitOrder.getAccount();
//		String odd = submitOrder.getOdd();
//		int type = submitOrder.getType();
//		long addressId = submitOrder.getAddressId();
//		List<OrderGoodsBo> goodsBoList = submitOrder.getGoodsList();
//		if (account == null || account.length() == 0 ||
//				odd == null || odd.length() == 0 ||
//				!Type.checkOrderSubmitType(type) || 
//				addressId <= 0 ||
//				goodsBoList == null || goodsBoList.size() == 0) {
//			return Constant.createReturn(ErrorCom.PARSE_ERROR);
//		}
//		
//		long curTime = System.currentTimeMillis();
//		
//		// 防止重复提交
//		TOrder oldTOrder = orderDao.findByOdd(odd);
//		if (oldTOrder != null) {
//			return Constant.createReturn(ErrorCom.REPEATED_SUBMIT);
//		}
//		
//		// 获取账号信息
//		TAccountInfo tAccountInfo = accountInfoDao.findByAccount(account);
//		if (tAccountInfo == null) {
//			return Constant.createReturn(ErrorCom.ACCOUNT_NOT_EXIST);
//		}
//		
//		// 获取地址信息
//		TAddress tAddress = addressDao.findOne(addressId);
//		if (tAddress == null) {
//			return Constant.createReturn(ErrorCom.ADDRESS_NOT_EXIST);
//		}
//		AddressBo address = tAddress.createBo();
//		
//		BigDecimal goodsPrice = BigDecimal.ZERO;
//		BigDecimal expPrice = BigDecimal.ZERO;
//		
//		List<TCommodityAttr> attrList = new ArrayList<TCommodityAttr>();
//		List<TCommodity> commodityList = new ArrayList<TCommodity>();
//		List<OrderCommodityBo> goodsList = new ArrayList<OrderCommodityBo>();
//		for (OrderGoodsBo orderGoods : goodsBoList) {
//			long communityId = orderGoods.getCommunityId();
//			long attrId = orderGoods.getAttrId();
//			int num = orderGoods.getNum();
//			
//			// 商品
//			TCommodity tCommodity = commodityDao.findOne(communityId);
//			if (tCommodity == null) {
//				return Constant.createReturn(ErrorCom.COMMODITY_NOT_EXIST);
//			}
//			
//			// 店铺
//			TShop tShop = shopDao.findOne(tCommodity.getShopId());
//			if (tShop == null) {
//				return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
//			}
//			
//			Specification<TCommodityAttr> spec = new Specification<TCommodityAttr>() {
//	    		@Override
//	    		public Predicate toPredicate(Root<TCommodityAttr> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//		
//	    			List<Predicate> predicates = new ArrayList<Predicate>();
//	    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.COMMODITY_ID).as(Long.class), communityId));
//	    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.ATTR_ID).as(Long.class), attrId));
//	    			
//	    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//	    		}
//			};
//			
//			TCommodityAttr tCommodityAttr = commodityAttrDao.findOne(spec);
//			if (tCommodityAttr == null) {
//				return Constant.createReturn(ErrorCom.COMMODITY_ATTR_NOT_EXIST);
//			}
//			
////			if (tCommodityAttr.getCommodityId() != tCommodity.getId()) {
////				return null;
////			}
//			
//			BigDecimal commodityExp = expressService.cacuFreight(address.getAddrId(), tCommodity.getAddrId());
//			if (commodityExp != null) {
//				if (commodityExp.compareTo(expPrice) > 0) {
//					expPrice = commodityExp;
//				}
//			}
//			
//			long stockNum = tCommodityAttr.getStockNum();
//			if (num < 1 || num > stockNum) {
//				return Constant.createReturn(ErrorCom.COMMODITY_ATTR_SOCK_NOT_ENOUGN);
//			}
//			
//			// 减库存
//			tCommodityAttr.setStockNum(stockNum - num);
//			tCommodityAttr.setUpdateTime(curTime);
//			attrList.add(tCommodityAttr);
//			//commodityAttrDao.save(tCommodityAttr);
//			
//			// 加销售量
//			tCommodity.setAllSaleNum(tCommodity.getAllSaleNum() + num);
//			tCommodity.setMonthSaleNum(tCommodity.getMonthSaleNum() + num);
//			tCommodity.setUpdateTime(curTime);
//			commodityList.add(tCommodity);
//			//commodityDao.save(tCommodity);
//			
//			goodsList.add(new OrderCommodityBo(
//					tCommodity.getId(), 
//					tCommodity.getShortTitle(),
//					tCommodity.getShowPic(),
//					tCommodityAttr.getAttrId(),
//					tCommodityAttr.getAttrName(),
//					tShop.getId(),
//					tShop.getName(),
//					tCommodityAttr.getPrice(),
//					num));
//
//			BigDecimal _price = tCommodityAttr.getPrice().multiply(new BigDecimal(num));
//			goodsPrice = goodsPrice.add(_price);
//		}
//		
//		String goodsInfo = GsonUtil.toString(goodsList);
//		if (goodsInfo == null) {
//			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
//		}
//		String addressInfo = GsonUtil.toString(address);
//		if (addressInfo == null) {
//			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
//		}
//		
//		TOrder tOrder = new TOrder(
//				0,
//				account,
//				odd,
//				Type.PENDING_PAYMENT_ORDER,
//				goodsPrice,
//				expPrice,
//				curTime + 24*60*60*100,
//				goodsInfo,
//				addressInfo,
//				"",
//				curTime,
//				curTime,
//				0,
//				0);
//		
//		
//		// 以下为真正保存
//		OrderInfoPo newOrderPo = submitSave(attrList, commodityList, tOrder);
//		
//		if (type == Type.ORDER_SUBMIT_MULTI) {
//			List<Long> commodityIds = new ArrayList<Long>();
//			for (TCommodity tCommodity : commodityList) {
//				commodityIds.add(tCommodity.getId());
//			}
//			trolleyService.batchDelByComodity(account, commodityIds);
//		}
//		
//		return Constant.createReturn(newOrderPo);
//	}
	
	
}
