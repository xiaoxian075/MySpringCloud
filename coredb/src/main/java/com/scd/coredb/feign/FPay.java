package com.scd.coredb.feign;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.PayService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.YftAsynNoticeBo;
import com.scd.joggle.pojo.po.*;
import com.scd.sdk.util.pojo.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/pay")
public class FPay {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
  @Resource
  private PayService payService;
    



  /**
   * APP支付请求
   * @param param
   * @return
   */
    @RequestMapping(value = "/requestPay")
	Return<PayInfoPo> requestPay(String account, String orderOdd) {
    	Return<PayInfoPo> ret = null;
    	try {
			ret = payService.requestPay(account, orderOdd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
    }
    
    /**
     * 余额支付
     * @param account
     * @param odd
     * @param payPassword
     * @return
     */
  	@RequestMapping(value = "/balancePay")
	Return<PayResultPo> balancePay(String account, String orderOdd, String payPassword) {
    	Return<PayResultPo> ret = null;
    	try {
			ret = payService.balancePay(account, orderOdd, payPassword);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
  	}
  	
    /**
     * 充值
     * @param account
     * @param odd
     * @param amount
     * @return
     */
    @RequestMapping(value = "/recharge")
	Return<PayInfoPo> recharge(String account, String odd, BigDecimal amount) {
    	Return<PayInfoPo> ret = null;
    	try {
			ret = payService.recharge(account, odd, amount);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
    }
    
    /**
     * APP支付结果通知
     * @param account
     * @param odd
     * @return
     */
    @RequestMapping(value = "/getPayResult")
	Return<PayResultPo> getPayResult(String account, String tradeOdd) {
    	Return<PayResultPo> ret = null;
    	try {
			ret = payService.getPayResult(account, tradeOdd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return ret;
    }

    /**
     * 云付通异步回调处理
     * @param info
     * @return
     */
    @RequestMapping(value = "/yunpayNotice")
	boolean yunpayNotice(String data) {
    	boolean ret = false;
    	try {
    		YftAsynNoticeBo yftAsynNoticeBo = payService.yunpayNotice(data);
    		if (yftAsynNoticeBo == null) {
    			return false;
    		}
			// 应该改为异步处理
			try {
				payService.dealYunpayNotice(yftAsynNoticeBo);
			} catch(Exception e) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
    	
		return ret;
    }
}
//    @Resource
//    private PayService payService;
//    @Autowired
//    private PayDao payDao;
//    @Autowired
//    private OrderService orderService;
//
//    @Transactional
//    @RequestMapping(value = "/order")
//    public synchronized Return<Object> payOrder(@RequestBody OrderPayPo orderPayPo) {
//        try {
//            Return<TPay> ret = null;
//            //如果不存在支付记录，则直接取出来
//            ret = payService.getPayInfoByOdd(orderPayPo.getOrderNumber());
//            if(ret.getData() == null){
//                ret = payService.pay(orderPayPo);
//                if (Return.isErr(ret)) {
//                    return Constant.createReturn(ret.getCode(), ret.getDesc());
//                }
//            }else{
//                //修改其他的支付方式
//                if(ret.getData().getPayWay() != orderPayPo.getPayWay()){
//                    payService.updatePayWay(orderPayPo.getPayWay(),ret.getData().getOrderNumber());
//                }
//            }
//
//            double amount = orderPayPo.getPayAmount().doubleValue();
//            //余额支付的时候修改订单状态，扣减余额增加智贝
//            if(orderPayPo.getPayWay() == 1){
//                //修改账户余额
//                payService.updateAccountAmount(orderPayPo.getAccount(),orderPayPo.getPayAmount());
//                //修改订单状态，并添加智贝
//                payService.updateOrderState(orderPayPo.getOrderNumber(),amount);
//                //添加推荐收益
//                payService.updateAccountEarnings(ret.getData().getOrderNumber(),ret.getData().getAccount(),ret.getData().getPayAmount(),Type.PAY_TYPE_SHOP);
//                //修改通知状态
//                payService.updateBalanceNoticeState(orderPayPo.getOrderNumber(),orderPayPo.getPayNumber());
//            }
//            //po.setAccount(ret.getData().getAccount());
//            //po.setOdd(ret.getData().getPayNumber());
//            //po.setGoodsPrice(ret.getData().getPayAmount());
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            //return Constant.createReturn(ErrorCom.ERROR);
//            throw new RuntimeException();
//        }
//        return Constant.createReturn();
//    }
//
//    /**
//     * 云支付异步通知
//     * @param param
//     * @return
//     */
//    @Transactional
//    @RequestMapping(value = "/yunpayNotice")
//    public Return<Object> yunpayNotice(String param) {
//        if (param == null) {
//            return Constant.createReturn(ErrorCom.PARSE_ERROR);
//        }
//        try {
//            JSONObject object = JSONObject.parseObject(param);
//            String payNumber = object.getString("orderId");
//            String tradeTime = object.getString("tradeTime");
//            String tradeNo = object.getString("tradeNo");
//
//            //修改订单状态
//            Return<TPay> ret = payService.getPayInfo(payNumber);
//            if(Return.isErr(ret)){
//                return Constant.createReturn(ret.getCode(), ret.getDesc());
//            }
//
//            PayPo payPo =  orderService.findByOdd(ret.getData().getOrderNumber());
//            if(payPo == null){
//                return Constant.createReturn(ret.getCode(), ret.getDesc());
//            }
//            //修改云付通通知状态
//            payService.updateNoticeState(payNumber, Long.parseLong(tradeTime),tradeNo);
//
//            double payAmount = ret.getData().getPayAmount().doubleValue();
//
//            //如果这笔订单是购物，则添加推荐人收益
//            if(ret.getData().getPayAct() == 1){
//                payService.updateAccountEarnings(ret.getData().getOrderNumber(),ret.getData().getAccount(),ret.getData().getPayAmount(),Type.PAY_TYPE_SHOP);
//                payService.updateOrderState(ret.getData().getOrderNumber(),payAmount);
//            }
//            //充值订单
//            if(ret.getData().getPayAct() == 3){
//                RechargePo rechargePo = new RechargePo();
//                rechargePo.setAccount(ret.getData().getAccount());
//                rechargePo.setAccountId(ret.getData().getAccountId());
//                rechargePo.setAmount(ret.getData().getPayAmount());
//                rechargePo.setOdd(ret.getData().getOrderNumber());
//                System.currentTimeMillis();
//                Return<TRecharge> retResult = payService.recharge(rechargePo);
//
//                if (Return.isErr(retResult)) {
//                    return Constant.createReturn(ret.getCode(), ret.getDesc());
//                }
//                payService.updateAccountAmountRecharge(ret.getData().getAccount(),ret.getData().getPayAmount());
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            //return Constant.createReturn(ErrorCom.ERROR);
//            throw  new RuntimeException();
//        }
//        return Constant.createReturn();
//    }
//
//    /**
//     * 充值接口
//     * @param rechargePo
//     * @return
//     */
//    @Transactional
//    @RequestMapping(value = "/recharge")
//    public Return<Object> payRecharge(@RequestBody RechargePo rechargePo) {
//        try {
//            if (rechargePo == null) {
//                return Constant.createReturn(ErrorCom.CHANGE_ERROR);
//            }
//
//            OrderPayPo orderPayPo = new OrderPayPo(
//                    0,
//                    rechargePo.getOdd(),
//                    rechargePo.getRechargeNumber(),
//                    rechargePo.getAccountId(),
//                    Type.SHOP_ID,
//                    rechargePo.getRechargeTime(),
//                    rechargePo.getPayWay(),
//                    rechargePo.getAmount(),
//                    Type.RECHARGE,
//                    rechargePo.getPayAct(),
//                    rechargePo.getAccount()
//            );
//            Return<TPay> retTPay = payService.pay(orderPayPo);
//            if (Return.isErr(retTPay)) {
//                return Constant.createReturn(retTPay.getCode(), retTPay.getDesc());
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            //return Constant.createReturn(ErrorCom.ERROR);
//            throw new RuntimeException();
//        }
//        return Constant.createReturn();
//    }
//
//    @Transactional
//    @RequestMapping(value = "/upgradeVip")
//    public Return<Object> payUpgradeVip(@RequestBody UpgradeVipPo upgradeVipPo) {
//        try {
//            if (upgradeVipPo == null) {
//                return Constant.createReturn(ErrorCom.CHANGE_ERROR);
//            }
//
//            Return<TUpgradeVip> ret = payService.upgradeVip(upgradeVipPo);
//            if (Return.isErr(ret)) {
//                return Constant.createReturn(ret.getCode(), ret.getDesc());
//            }
//
//            OrderPayPo orderPayPo = new OrderPayPo(
//                    ret.getData().getId(),
//                    ret.getData().getOdd(),
//                    upgradeVipPo.getUpgradeVipNumber(),
//                    upgradeVipPo.getAccountId(),
//                    Type.SHOP_ID,
//                    upgradeVipPo.getUpgradeVipTime(),
//                    upgradeVipPo.getPayWay(),
//                    upgradeVipPo.getAmount(),
//                    Type.VIP_UP,
//                    upgradeVipPo.getPayAct(),
//                    upgradeVipPo.getAccount()
//            );
//            Return<TPay> retTPay = payService.pay(orderPayPo);
//            if (Return.isErr(retTPay)) {
//                return Constant.createReturn(retTPay.getCode(), retTPay.getDesc());
//            }
//            //余额支付
//            if(upgradeVipPo.getPayWay() == 1){
//                //修改用户余额并且修改用户等级
//                payService.updateAccountAmount(orderPayPo.getAccount(),orderPayPo.getPayAmount());
//                payService.updateAccountInfo(orderPayPo.getAccount());
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            //return Constant.createReturn(ErrorCom.ERROR);
//            throw new RuntimeException();
//        }
//        return Constant.createReturn();
//    }
//
//    @RequestMapping(value = "/getPayList")
//    public Return<PageInfo<OrderPayPo>> getPayList(String account,int page, int size) {
//        PageInfo<OrderPayPo> pPage = null;
//        try {
//            Specification<TPay> spec = new Specification<TPay>() {
//                @Override
//                public Predicate toPredicate(Root<TPay> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                    List<Predicate> predicates = new ArrayList<Predicate>();
//                    predicates.add(criteriaBuilder.equal(root.get(TPay.ACCOUNT), account));
//                    predicates.add(criteriaBuilder.notEqual(root.get(TPay.YUNPAYNOTICESTATE), 0));
//                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//                }
//            };
//            Sort.Order order = new Sort.Order(Sort.Direction.DESC, TPay.NOTICE_TIME);
//            Pageable pageable = new PageRequest(page, size, new Sort(order));
//            Page<TPay> pageInfo = payDao.findAll(spec, pageable);
//            if (pageInfo == null) {
//                return null;
//            }
//            List<TPay> list = pageInfo.getContent();
//            int total = (int) pageInfo.getTotalElements();
//            if (list == null) {
//                return null;
//            }
//            List<OrderPayPo> listPo = new ArrayList<OrderPayPo>();
//            for (TPay tPay : list) {
//                OrderPayPo po = new OrderPayPo(
//                        tPay.getOrderId(),
//                        tPay.getOrderNumber(),
//                        tPay.getPayNumber(),
//                        tPay.getAccountId(),
//                        tPay.getShopId(),
//                        tPay.getPayTime(),
//                        tPay.getPayWay(),
//                        tPay.getPayAmount(),
//                        tPay.getPayType(),
//                        tPay.getPayAct(),
//                        tPay.getAccount()
//                );
//                listPo.add(po);
//            }
//            pPage = new PageInfo<OrderPayPo>(page, total, listPo);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return null;
//        }
//        return Constant.createReturn(pPage);
//    }
//
//
//    @RequestMapping(value = "/getPayInfoByOrderId")
//    public Return<OrderPayPo> getPayInfoByOrderId(String orderId) {
//        OrderPayPo orderPayPo = new OrderPayPo();
//        Return<TPay> payInfo = payService.getPayInfo(orderId);
//        if(payInfo != null){
//            orderPayPo.setAccount(payInfo.getData().getAccount());
//            orderPayPo.setOrderNumber(payInfo.getData().getOrderNumber());
//            orderPayPo.setPayAct(payInfo.getData().getPayAct());
//            orderPayPo.setPayType(payInfo.getData().getPayType());
//            orderPayPo.setPayWay(payInfo.getData().getPayWay());
//            orderPayPo.setYunpayNoticeState(payInfo.getData().getYunpayNoticeState());
//        }
//        return Constant.createReturn(orderPayPo);
//    }
//
//
//    @RequestMapping(value = "/getPayInfoByOdd")
//    public Return<OrderPayPo> getPayInfoByOdd(String odd) {
//        OrderPayPo orderPayPo = new OrderPayPo();
//        Return<TPay> payInfo = payService.getPayInfoByOdd(odd);
//        if(payInfo != null){
//            orderPayPo.setAccount(payInfo.getData().getAccount());
//            orderPayPo.setOrderNumber(payInfo.getData().getOrderNumber());
//            orderPayPo.setPayAct(payInfo.getData().getPayAct());
//            orderPayPo.setPayType(payInfo.getData().getPayType());
//            orderPayPo.setPayWay(payInfo.getData().getPayWay());
//            orderPayPo.setYunpayNoticeState(payInfo.getData().getYunpayNoticeState());
//            orderPayPo.setPayNumber(payInfo.getData().getPayNumber());
//        }
//        return Constant.createReturn(orderPayPo);
//    }

