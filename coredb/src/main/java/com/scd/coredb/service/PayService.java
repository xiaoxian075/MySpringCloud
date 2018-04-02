package com.scd.coredb.service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.pojo.bo.OrderPayBo;
import com.scd.coredb.pojo.db.TPayRecord;
import com.scd.coredb.pojo.db.TPaySdk;
import com.scd.coredb.pojo.util.PayRecordUtil;
import com.scd.coredb.third.ThirdYft;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.bo.YftAsynNoticeBo;
import com.scd.joggle.pojo.po.PayInfoPo;
import com.scd.joggle.pojo.po.PayResultPo;
import com.scd.sdk.util.RandomUtil;
import com.scd.sdk.util.TimeUtil;
import com.scd.sdk.util.pojo.Return;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import javax.annotation.Resource;


@Service
public class PayService {
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private OrderService orderService;
	
	@Resource
	private PaySdkService paySdkService;
	
	@Resource
	private PayRecordService payRecordService;
	
	@Resource
	private TransferBalanceService transferBalanceService;
	
	@Resource
	private PayAsynNoticeService payAsynNoticeService;
	
	public Return<PayInfoPo> requestPay(String account, String orderOdd) {
		Return<TPaySdk> ret = readOrWriteRequestPay(account, orderOdd);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		PayInfoPo payInfo = ThirdYft.getInstance().createPayInfo(ret.getData());
		if (payInfo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(payInfo);
	}
	
	public Return<PayInfoPo> recharge(String account, String clientOdd, BigDecimal amount) {
		Return<TPaySdk> ret = readOrWriteRecharge(account, clientOdd, amount);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		PayInfoPo payInfo = ThirdYft.getInstance().createPayInfo(ret.getData());
		if (payInfo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(payInfo);
	}
	
	/**
	 * 余额支付
	 * @param account
	 * @param odd
	 * @param payPassword
	 * @return
	 */
	@Transactional
	public synchronized Return<PayResultPo> balancePay(String account, String orderOdd, String payPassword) {
		// 余额支付也生成这样一条记录
		Return<TPaySdk> ret = readOrWriteBalancePay(account, orderOdd, payPassword);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		TPaySdk tPaySdk = ret.getData();
		
		// 更新账户余额(客户往平台转账)
		if (!transferBalanceService.transferBalance(tPaySdk.getPayAccount(), tPaySdk.getReceiveAccount(), tPaySdk.getAmount())) {
			throw new RuntimeException();
		}
		
		// 更新订单状态
		if (!orderService.dealPay(orderOdd, tPaySdk.getOrderType())) {
			throw new RuntimeException();
		}
		
		// 产生流水
		String strTradeTime = TimeUtil.changeToStr(System.currentTimeMillis(), "yyyyMMddHHmmss");
		if (strTradeTime == null) {
			throw new RuntimeException();
		}
		TPayRecord tPayRecord = createPayRecord(tPaySdk, strTradeTime);
		if (tPayRecord == null) {
			throw new RuntimeException();
		}
		
		// 计算返利（平台账号返现给支付账号的推荐人）
		if (!transferBalanceService.dealEarnings(tPaySdk.getPayAccount(), tPaySdk.getReceiveAccount(), tPaySdk.getAmount())) {
			throw new RuntimeException();
		}
		
		// 更新TPaySdk状态为成功
		if (!setPaySdkToSucc(tPaySdk)) {
			throw new RuntimeException();
		}
		
		PayResultPo po = PayRecordUtil.changeToResult(tPayRecord);
		if (po == null) {
			throw new RuntimeException();
		}
		return Constant.createReturn(po);
	}
	
	
	/**
	 * 云付通回调处理
	 * @param info
	 * @return
	 */
	public YftAsynNoticeBo yunpayNotice(String data) {
		// 解析
		YftAsynNoticeBo info = ThirdYft.getInstance().paramAsynNotice(data);
		if (info == null) {
			return null;
		}
		
		// 只处理支付通知
		int noticeType = info.getNoticeType();
		int state = info.getState();
		if (noticeType == Type.PAY_NOTICE_PAY) {
			if (state != Type.YFT_ASYN_NOTICE_HASPAY) {
				return null;
			}
			// 将回调回来的记录存储到数据库
			if (!payAsynNoticeService.save(info)) {
				return null;
			}
			
			return info;
		} else if (noticeType == Type.PAY_NOTICE_ROLL){
			//取消付款
			YftAsynNoticeBo hasBo = payAsynNoticeService.get(info.getTradeNo());
			if (hasBo == null) {
				return null;
			}
			
			TPaySdk tPaySdk = paySdkService.getByOdd(hasBo.getTradeNo());
			if (tPaySdk == null) {
				return null;
			}
			if (tPaySdk.getState() != Type.PAY_SDK_REQUEST) {
				return null;
			}
			
			tPaySdk.setState(Type.PAY_SDK_CANCLE);
			tPaySdk.setUpdateTime(System.currentTimeMillis());
			if (!paySdkService.save(tPaySdk)) {
				return null;
			}
			
			hasBo.setNoticeType(Type.PAY_NOTICE_ROLL);
			if (!payAsynNoticeService.save(hasBo)) {
				return null;
			}
			return hasBo;
		} else {
			return null;
		}
	}

	@Transactional
	public synchronized void dealYunpayNotice(YftAsynNoticeBo info) {
		TPaySdk tPaySdk = paySdkService.getByOdd(info.getTradeNo());
		if (tPaySdk == null) {
			return;
		}
		if (tPaySdk.getState() == Type.PAY_SDK_SUCC) {
			return;
		}
		
	    String payYunId = info.getPayYunId();
	    //String orderId = info.getOrderId();
	    BigDecimal realAmount = info.getRealAmount();
	    String tradeTime = info.getTradeTime();
	    
	    // 对TPaySdk设置以保证以支付回调的数据为准
	    tPaySdk.setPayAccount(payYunId);
	    //tPaySdk.setOrderOdd(orderId);
	    tPaySdk.setAmount(realAmount);
	    
	    int act = tPaySdk.getAct();
	    if (act == Type.PAY_ACT_SHOP) {
			
			// 更新订单状态
			if (!orderService.dealPay(tPaySdk.getOrderOdd(), tPaySdk.getOrderType())) {
				throw new RuntimeException();
			}
			
			// 产生流水
			TPayRecord tPayRecord = createPayRecord(tPaySdk, tradeTime);
			if (tPayRecord == null) {
				throw new RuntimeException();
			}
			
			// 计算返利（平台账号返现给支付账号的推荐人）
			if (!transferBalanceService.dealEarnings(tPaySdk.getPayAccount(), tPaySdk.getReceiveAccount(), tPaySdk.getAmount())) {
				throw new RuntimeException();
			}
			
			// 更新TPaySdk状态为成功
			if (!setPaySdkToSucc(tPaySdk)) {
				throw new RuntimeException();
			}
	    } else if (act == Type.PAY_ACT_RECHARGE) {
	    	// 产生流水
			TPayRecord tPayRecord = createPayRecord(tPaySdk, tradeTime);
			if (tPayRecord == null) {
				throw new RuntimeException();
			}
			
			// 给充值账上加钱
			if (!transferBalanceService.addAmountToBalance(tPaySdk.getPayAccount(), tPaySdk.getAmount())) {
				throw new RuntimeException();
			}
			
			// 更新TPaySdk状态为成功
			if (!setPaySdkToSucc(tPaySdk)) {
				throw new RuntimeException();
			}
	    }
	}

	public Return<PayResultPo> getPayResult(String account, String orderOdd) {
		TPayRecord tPayRecord = payRecordService.getByOrderOdd(orderOdd);
		if (tPayRecord == null) {
/*			TPaySdk tPaySdk = paySdkService.getByOdd(orderOdd);
			if (tPaySdk == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}
			if (tPaySdk.getState() != Type.PAY_SDK_REQUEST) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}
			YftAsynNoticeBo yftAsynNoticeBo = payAsynNoticeService.get(orderOdd);
			if (yftAsynNoticeBo == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}
			
			try {
				dealYunpayNotice(yftAsynNoticeBo);
			} catch(Exception e) {
				return Constant.createReturn(ErrorCom.ERROR);
			}
			
			tPayRecord = payRecordService.getByTradeOdd(orderOdd);
			if (tPayRecord == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}*/
			return Constant.createReturn(ErrorCom.PAY_RESULT_NOT_EXIST); 
		}
		
		if (!account.equals(tPayRecord.getPayAccount())) {
			return Constant.createReturn(ErrorCom.NO_POWER);
		}
		
		PayResultPo po = PayRecordUtil.changeToResult(tPayRecord);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(po);
	}
	
	/**
	 * 产生交易流水
	 * @param tPaySdk
	 * @return
	 */
	private TPayRecord createPayRecord(TPaySdk tPaySdk, String tradeTime) {
		if (tPaySdk == null) {
			return null;
		}

		long curTime = System.currentTimeMillis();		
		TPayRecord tPayRecord = new TPayRecord(
				0L,
				tPaySdk.getOdd(),
				tPaySdk.getOrderOdd(),
				tPaySdk.getAct(),
				tPaySdk.getPayWay(),
				tPaySdk.getOrderType(),
				tPaySdk.getPayAccount(),
				tPaySdk.getReceiveAccount(),
				tPaySdk.getAmount(),
				tradeTime,
				curTime
				);
		

		TPayRecord newTPayRecord = payRecordService.save(tPayRecord);
		if (newTPayRecord == null) {
			return null;
		}
		
		return newTPayRecord;
	}

	private boolean setPaySdkToSucc(TPaySdk tPaySdk) {
		if (tPaySdk == null) {
			return false;
		}
		tPaySdk.setState(Type.PAY_SDK_SUCC);
		tPaySdk.setUpdateTime(System.currentTimeMillis());
		if (!paySdkService.save(tPaySdk)) {
			return false;
		}
		
		return true;
	}
	private Return<TPaySdk> readOrWriteRequestPay(String account, String orderOdd) {
		if (orderOdd == null || orderOdd.length() == 0) {
			return Constant.createReturn(ErrorCom.PARSE_ERROR);
		}
		String clientId = ThirdYft.getInstance().getYftYzjClientId();
		long yftYzjPayTimeout = ThirdYft.getInstance().getYftYzjPayTimeout();
		String receiveAccount = ThirdYft.getInstance().getYftYzjReceiveAccount();
		if (clientId == null || yftYzjPayTimeout <= 0 || receiveAccount == null) {
			return Constant.createReturn(ErrorCom.CONFIG_ERROR);
		}
		long curTime = System.currentTimeMillis();
		long lastPayTime = curTime + yftYzjPayTimeout;
		
		// 检查记录是否已经存在了
		TPaySdk tPaySdk = paySdkService.getByOrderOdd(orderOdd);
		if (tPaySdk == null) {
			// 检查账号
			AccountBo accountInfo = accountService.getAccountByAccount(account);
			if (accountInfo == null) {
				return Constant.createReturn(ErrorCom.ACCOUNT_NOT_EXIST);
			}
			int payState = accountInfo.getPayState();
			if (payState != Type.PAY_STATE_USABLE) {
				return Constant.createReturn(ErrorCom.ACCOUNT_NOT_PAY);
			}
			
			// 检查订单
			OrderPayBo orderPayBo = orderService.caclAmount(orderOdd);
			if (orderPayBo == null) {
				return Constant.createReturn(ErrorCom.ORDER_NOT_EXIST);
			}

			String odd = RandomUtil.getYftOdd(clientId);
			if (odd == null) {
				return Constant.createReturn(ErrorCom.CREATE_ODD_ERROR);
			}
			String clientOdd = RandomUtil.getOdd();
			tPaySdk = new TPaySdk(
					0L,
					clientId,
					odd,
					orderOdd,
					clientOdd,
					Type.PAY_ACT_SHOP,
					Type.PAY_WAY_SDK,
					orderPayBo.getType(),
					account,
					receiveAccount,
					orderPayBo.getAmount(),
					curTime,
					curTime,
					Type.PAY_SDK_REQUEST,
					curTime,
					curTime
					);
		} else {
			// 判断账号是否一致
			if (!account.equals(tPaySdk.getPayAccount())) {
				return Constant.createReturn(ErrorCom.NO_POWER);
			}
			// 处理状态
			if (Type.PAY_SDK_CANCLE == tPaySdk.getState()) {
				String odd = RandomUtil.getYftOdd(clientId);
				if (odd == null) {
					return Constant.createReturn(ErrorCom.CREATE_ODD_ERROR);
				}
				tPaySdk.setOdd(odd);
			} else if (Type.PAY_SDK_REQUEST != tPaySdk.getState()) {
				return Constant.createReturn(ErrorCom.STATE_FAIL);
			}
			if (Type.PAY_ACT_SHOP != tPaySdk.getAct()) {
				return Constant.createReturn(ErrorCom.STATE_FAIL);
			}
			// 如果不是SDK支付，改回用SDK支付
			if (Type.PAY_WAY_SDK != tPaySdk.getPayWay()) {
				tPaySdk.setPayWay(Type.PAY_WAY_SDK);
			}
		}
		tPaySdk.setLastPayTime(lastPayTime);
		tPaySdk.setUpdateTime(curTime);
		
		if (!paySdkService.save(tPaySdk)) {
			return Constant.createReturn(ErrorCom.SAVE_ERROR);
		}
		
		return Constant.createReturn(tPaySdk);
	}
	
	private Return<TPaySdk> readOrWriteRecharge(String account, String clientOdd, BigDecimal amount) {
		String clientId = ThirdYft.getInstance().getYftYzjClientId();
		long yftYzjPayTimeout = ThirdYft.getInstance().getYftYzjPayTimeout();
		String receiveAccount = ThirdYft.getInstance().getYftYzjReceiveAccount();
		if (clientId == null || yftYzjPayTimeout <= 0 || receiveAccount == null) {
			return Constant.createReturn(ErrorCom.CONFIG_ERROR);
		}
		long curTime = System.currentTimeMillis();
		long lastPayTime = curTime + yftYzjPayTimeout;
		
		// 检查记录是否已经存在了
		TPaySdk tPaySdk = paySdkService.getByClientOdd(clientOdd);
		if (tPaySdk == null) {
			// 检查账号
			AccountBo accountInfo = accountService.getAccountByAccount(account);
			if (accountInfo == null) {
				return Constant.createReturn(ErrorCom.ACCOUNT_NOT_EXIST);
			}
			int payState = accountInfo.getPayState();
			if (payState != Type.PAY_STATE_USABLE) {
				return Constant.createReturn(ErrorCom.ACCOUNT_NOT_PAY);
			}

			String odd = RandomUtil.getYftOdd(clientId);
			if (odd == null) {
				return Constant.createReturn(ErrorCom.CREATE_ODD_ERROR);
			}
			tPaySdk = new TPaySdk(
					0L,
					clientId,
					odd,
					"",		// 充值无订单号，置空
					clientOdd,
					Type.PAY_ACT_RECHARGE,
					Type.PAY_WAY_SDK,
					0,
					account,
					receiveAccount,
					amount,
					curTime,
					curTime,
					Type.PAY_SDK_REQUEST,
					curTime,
					curTime
					);
		} else {
			// 判断账号是否一致
			if (!account.equals(tPaySdk.getPayAccount())) {
				return Constant.createReturn(ErrorCom.NO_POWER);
			}
			// 处理状态
			if (Type.PAY_SDK_CANCLE == tPaySdk.getState()) {
				String odd = RandomUtil.getYftOdd(clientId);
				if (odd == null) {
					return Constant.createReturn(ErrorCom.CREATE_ODD_ERROR);
				}
				tPaySdk.setOdd(odd);
			} else if (Type.PAY_SDK_REQUEST != tPaySdk.getState()) {
				return Constant.createReturn(ErrorCom.STATE_FAIL);
			}
			if (Type.PAY_ACT_RECHARGE != tPaySdk.getAct()) {
				return Constant.createReturn(ErrorCom.STATE_FAIL);
			}
		}
		tPaySdk.setLastPayTime(lastPayTime);
		tPaySdk.setUpdateTime(curTime);
		
		if (!paySdkService.save(tPaySdk)) {
			return Constant.createReturn(ErrorCom.SAVE_ERROR);
		}
		
		return Constant.createReturn(tPaySdk);
	}
	
	private Return<TPaySdk> readOrWriteBalancePay(String account, String orderOdd, String payPassword) {
		if (orderOdd == null || orderOdd.length() == 0) {
			return Constant.createReturn(ErrorCom.PARSE_ERROR);
		}
		String clientId = ThirdYft.getInstance().getYftYzjClientId();
		long yftYzjPayTimeout = ThirdYft.getInstance().getYftYzjPayTimeout();
		String receiveAccount = ThirdYft.getInstance().getYftYzjReceiveAccount();
		if (clientId == null || yftYzjPayTimeout <= 0 || receiveAccount == null) {
			return Constant.createReturn(ErrorCom.CONFIG_ERROR);
		}
		long curTime = System.currentTimeMillis();
		long lastPayTime = curTime + yftYzjPayTimeout;
		
		AccountBo accountInfo = accountService.getAccountByAccount(account);
		if (accountInfo == null) {
			return Constant.createReturn(ErrorCom.ACCOUNT_NOT_EXIST);
		}
		
		// 检查记录是否已经存在了
		TPaySdk tPaySdk = paySdkService.getByOrderOdd(orderOdd);
		if (tPaySdk == null) {
			// 检查账号并验证支付密码
//			AccountBo accountInfo = accountService.getAccountByAccount(account);
//			if (accountInfo == null) {
//				return Constant.createReturn(ErrorCom.ACCOUNT_NOT_EXIST);
//			}
			String dbPayPassword = accountInfo.getPayPassword();
			if(dbPayPassword == null || dbPayPassword.length() == 0) {
				//没有设置支付密码
				return Constant.createReturn(ErrorCom.SET_PAY_PASSWORD);
			}
			if(!dbPayPassword.equals(payPassword)){
				//支付密码不正确
				return Constant.createReturn(ErrorCom.ERR_PAY_PASSWORD);
			}
			
			// 检查订单
			OrderPayBo orderPayBo = orderService.caclAmount(orderOdd);
			if (orderPayBo == null) {
				return Constant.createReturn(ErrorCom.ORDER_NOT_EXIST);
			}
			
			String odd = RandomUtil.getYftOdd(clientId);
			if (odd == null) {
				return Constant.createReturn(ErrorCom.CREATE_ODD_ERROR);
			}
			String clientOdd = RandomUtil.getOdd();
			tPaySdk = new TPaySdk(
					0L,
					clientId,
					odd,
					orderOdd,
					clientOdd,
					Type.PAY_ACT_SHOP,
					Type.PAY_WAY_BALANCE,
					orderPayBo.getType(),
					account,
					receiveAccount,
					orderPayBo.getAmount(),
					curTime,
					curTime,
					Type.PAY_SDK_REQUEST,
					curTime,
					curTime
					);
		} else {
			// 判断账号是否一致
			if (!account.equals(tPaySdk.getPayAccount())) {
				return Constant.createReturn(ErrorCom.NO_POWER);
			}
			// 判断状态是否正确
			if (Type.PAY_SDK_REQUEST != tPaySdk.getState()) {
				return Constant.createReturn(ErrorCom.STATE_FAIL);
			}
			if (Type.PAY_ACT_SHOP != tPaySdk.getAct()) {
				return Constant.createReturn(ErrorCom.STATE_FAIL);
			}
			// 如果不是余额支付，改回用余额支付
			if (Type.PAY_WAY_BALANCE != tPaySdk.getPayWay()) {
				tPaySdk.setPayWay(Type.PAY_WAY_BALANCE);
			}
		}
		
		if (accountInfo.getBalance().compareTo(tPaySdk.getAmount()) < 0) {
			return Constant.createReturn(ErrorCom.ACCOUNT_BALANCE_NOT_ENOUGN);
		}
		
		tPaySdk.setLastPayTime(lastPayTime);
		tPaySdk.setUpdateTime(curTime);
		
		if (!paySdkService.save(tPaySdk)) {
			return Constant.createReturn(ErrorCom.SAVE_ERROR);
		}
		
		return Constant.createReturn(tPaySdk);
	}
}

//    @Autowired
//    private PayDao payDao;
//    @Autowired
//    private OrderDao orderDao;
//    @Autowired
//    private AccountBalanceDao accountBalanceDao;
//    @Autowired
//    private RechargeDao rechargeDao;
//    @Autowired
//    private UpgradeVipDao upgradeVipDao;
//    @Autowired
//    private AccountInfoDao accountInfoDao;
//    @Autowired
//    private TTotalEarningsDao tTotalEarningsDao;
//
//
//    public Return<TPay> pay(OrderPayPo orderPayPo) {
//        if (orderPayPo == null) {
//            return Constant.createReturn(ErrorCom.ORDER_PARAM_ERROR);
//        }
//        TPay tPay = new TPay(
//                orderPayPo.getOrderId(),
//                orderPayPo.getPayNumber(),
//                orderPayPo.getAccountId(),
//                orderPayPo.getShopId(),
//                orderPayPo.getPayTime(),
//                orderPayPo.getPayWay(),
//                orderPayPo.getPayAmount(),
//                orderPayPo.getPayType(),
//                orderPayPo.getPayAct(),
//                orderPayPo.getAccount(),
//                orderPayPo.getOrderNumber()
//        );
//        try {
//            TPay retultPay = payDao.save(tPay);
//            if (retultPay == null) {
//                return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
//            }
//            return Constant.createReturn(retultPay);
//        } catch (Exception e) {
//            log.info(e.getMessage());
//            return Constant.createReturn(ErrorCom.ERROR);
//        }
//    }
//
//    @Autowired
//    private OrderMergePayDao orderMergePayDao;
//
//    @Transactional
//    public void updateOrderState(String odd, double payAmount) {
//
//        TOrder tOrder = orderDao.findByOdd(odd);
//        if (tOrder != null) {
//            long integral = new BigDecimal(payAmount).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
//            if (1 != orderDao.updateOrderState(Type.PENDING_SHIPMENT_ORDER, System.currentTimeMillis(), integral, odd)) {
//                throw new RuntimeException();
//            }
//            return;
//        }
//
//        TOrderMergePay tOrderMergePay = orderMergePayDao.findByOdd(odd);
//        if (tOrderMergePay == null) {
//            throw new RuntimeException();
//        }
//
//        String strOdd = tOrderMergePay.getChildOdd();
//        List<String> oddList = GsonUtil.toJson(strOdd, new TypeToken<List<String>>() {}.getType());
//        if (oddList == null) {
//            throw new RuntimeException();
//        }
//        for (String _odd : oddList) {
//            TOrder _tOrder = orderDao.findByOdd(_odd);
//            if (_tOrder == null) {
//                throw new RuntimeException();
//            }
//            BigDecimal _allAmount = _tOrder.getGoodsPrice();
//            _allAmount = _allAmount.add(_tOrder.getExpPrice());
//
//            long integral = _allAmount.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
//            if (1 != orderDao.updateOrderState(Type.PENDING_SHIPMENT_ORDER, System.currentTimeMillis(), integral, _odd)) {
//                throw new RuntimeException();
//            }
//        }
//    }
//
//    public int updateBalanceNoticeState(String odd, String orderNumber) {
//        return payDao.updateBalanceNoticeState(1, System.currentTimeMillis(), System.currentTimeMillis(), odd, orderNumber);
//    }
//
//    public int updateAccountAmount(String account, BigDecimal amount) {
//        return accountBalanceDao.updateAccountAmount(amount, account);
//    }
//
//    public int updateAccountAmountRecharge(String account, BigDecimal amount) {
//        return accountBalanceDao.updateAccountAmountRecharge(amount, account);
//    }
//
//    public Return<TPay> getPayInfo(String payNumber) {
//        TPay tPay = payDao.findByPayNumber(payNumber);
//        if (tPay == null) {
//            return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
//        }
//        return Constant.createReturn(tPay);
//    }
//
//    public Return<TPay> getPayInfoByOdd(String orderNumber) {
//        TPay tPay = payDao.findByOrderNumber(orderNumber);
//        if (tPay == null) {
//            return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
//        }
//        return Constant.createReturn(tPay);
//    }
//
//    public int updateNoticeState(String odd, long payTime, String tradeNo) {
//        long noticeTime = System.currentTimeMillis();
//        return payDao.updateNoticeState(Type.NOTICC_SUCC, noticeTime, payTime, odd, tradeNo);
//    }
//
//    public Return<TRecharge> recharge(RechargePo rechargePo) {
//        if (rechargePo == null) {
//            return Constant.createReturn(ErrorCom.ORDER_PARAM_ERROR);
//        }
//        TRecharge tRecharge = new TRecharge(
//                rechargePo.getAccountId(),
//                rechargePo.getAccount(),
//                rechargePo.getAmount(),
//                System.currentTimeMillis(),
//                rechargePo.getOdd()
//        );
//        try {
//            TRecharge retult = rechargeDao.save(tRecharge);
//            if (retult == null) {
//                return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
//            }
//            return Constant.createReturn(retult);
//        } catch (Exception e) {
//            log.info(e.getMessage());
//            return Constant.createReturn(ErrorCom.ERROR);
//        }
//    }
//
//    public Return<TUpgradeVip> upgradeVip(UpgradeVipPo upgradeVipPo) {
//        if (upgradeVipPo == null) {
//            return Constant.createReturn(ErrorCom.ORDER_PARAM_ERROR);
//        }
//        TUpgradeVip tUpgradeVip = new TUpgradeVip(
//                upgradeVipPo.getAccountId(),
//                upgradeVipPo.getAccount(),
//                System.currentTimeMillis(),
//                upgradeVipPo.getOdd()
//        );
//        try {
//            TUpgradeVip retult = upgradeVipDao.save(tUpgradeVip);
//            if (retult == null) {
//                return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
//            }
//            return Constant.createReturn(retult);
//        } catch (Exception e) {
//            log.info(e.getMessage());
//            return Constant.createReturn(ErrorCom.ERROR);
//        }
//    }
//
//
//    public int updateAccountInfo(String account) {
//        return accountInfoDao.updateAccountInfo(Type.MEMBER_VIP, account);
//    }
//
//    /**
//     * 添加推荐人收益
//     *
//     * @param odd
//     * @param account
//     * @param price
//     * @param type
//     * @return
//     */
//    @Transactional
//    public boolean updateAccountEarnings(String odd, String account, BigDecimal price, int type) {
//        boolean result = false;
//        TAccountInfo byAccount = accountInfoDao.findByAccount(account);
//        if (byAccount == null) {
//            return result;
//        }
//        String referee = byAccount.getReferee();
//        //消费金额的百分之一
//        BigDecimal earnings = price.multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_HALF_UP);
//        TTotalEarnings tTotalEarnings = new TTotalEarnings(
//                0,
//                referee,//收益账号
//                odd,
//                account,//消费账号
//                earnings,
//                System.currentTimeMillis(),
//                type);//购物收益
//        int i = accountBalanceDao.updateAccountTotalEarnings(earnings, referee);
//        if (i <= 0) {
//            return result;
//        }
//        TTotalEarnings save = tTotalEarningsDao.save(tTotalEarnings);
//        if (save != null) {
//            result = true;
//        } else {
//            throw new RuntimeException();
//        }
//        return result;
//    }
//
//    public int updatePayWay(int payWay, String orderNumber) {
//        return payDao.updatePayWay(payWay, orderNumber);
//    }
