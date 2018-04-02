package com.scd.admin.controller.accept;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BasePageAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayRecordAcceptMsg extends BasePageAcceptMsg {
	
//    private String account;
//    private String payNumber;
//    private String orderNumber;
//    private int payWay;
//    private int payAct;
	private String tradeOdd;
	private String orderOdd;
	private String payAccount;
	private String receiveAccount;
	private int payWay;
	private int act;
    	
	@Override
	public boolean check() {
		if (tradeOdd != null && tradeOdd.length() == 0) {
			tradeOdd = null;
		}
		if (orderOdd != null && orderOdd.length() == 0) {
			orderOdd = null;
		}
		if (payAccount != null && payAccount.length() == 0) {
			payAccount = null;
		}
		if (receiveAccount != null && receiveAccount.length() == 0) {
			receiveAccount = null;
		}
		if (payWay != 0 && !Type.checkPayState(payWay)) {
			return false;
		}
		if (act != 0 && !Type.checkPayAct(act)) {
			return false;
		}
		
		page = getPage(page);
		size = getSize(size);
		return true;
	}
}
