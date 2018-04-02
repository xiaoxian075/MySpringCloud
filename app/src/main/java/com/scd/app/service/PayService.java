package com.scd.app.service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FPay;
import com.scd.app.feign.FPayRecord;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.*;
import com.scd.sdk.util.pojo.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class PayService {

	@Autowired
    private FPay fPay;
	
	@Autowired
    private FPayRecord fPayRecord;


    public Return<PayInfoPo> requestPay(String account, String orderOdd) {
    	Return<PayInfoPo> ret = fPay.requestPay(account, orderOdd);
    	if (Return.isErr(ret)) {
    		return Constant.createReturn(ret.getCode(), ret.getDesc());
    	}
    	
    	PayInfoPo po = ret.getData();
    	if (po == null) {
    		return Constant.createReturn(ErrorCom.ERROR);
    	}
    	
    	return Constant.createReturn(po);
    }
    
    public Return<PayInfoPo> recharge(String account,String odd,BigDecimal amount) {
    	Return<PayInfoPo> ret = fPay.recharge(account, odd, amount);
    	if (Return.isErr(ret)) {
    		return Constant.createReturn(ret.getCode(), ret.getDesc());
    	}
    	
    	PayInfoPo po = ret.getData();
    	if (po == null) {
    		return Constant.createReturn(ErrorCom.ERROR);
    	}
    	
    	return Constant.createReturn(po);
    }
    
    public Return<PayResultPo> balancePay(String account, String orderOdd, String payPassword) {
    	return fPay.balancePay(account, orderOdd, payPassword);
	}
    
    public Return<PayResultPo> getPayResult(String account, String tradeOdd) {
    	return fPay.getPayResult(account, tradeOdd);
	}
    
	public Return<List<PayResultPo>> getPayList(int page, int size, String account) {
		return fPayRecord.getList(page, size, account);
	}
    
    /**
     * 云付通异步通知处理
     */
    public boolean yunpayNotice(String data) {
		if (data == null || data.length() == 0) {
			return false;
		}
		
//		// 解析
//		YftAsynNoticeBo info = ThirdYft.getInstance().paramAsynNotice(data);
//		if (info == null) {
//			return false;
//		}
		
		// 业务处理
		if (!fPay.yunpayNotice(data)) {
			return false;
		}
		
		return true;
    }
}
