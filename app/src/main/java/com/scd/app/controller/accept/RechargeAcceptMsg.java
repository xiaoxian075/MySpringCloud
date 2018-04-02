package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
public class RechargeAcceptMsg extends BaseAcceptMsg {

    private String odd;//订单编号
    private String amount;//金额

    @Override
    public boolean check() {
        if(odd == null || odd.length() == 0 || odd.length() > 32){
            return false;
        }
        try {
	        BigDecimal bAmount = new BigDecimal(amount);
	        if (bAmount == null || bAmount.compareTo(BigDecimal.ZERO) <= 0) {
	        	return false;
	        }
	        bAmount = bAmount.setScale(2);
	        amount = bAmount.toString();
        } catch(Exception e) {
        	return false;
        }
        return true;
    }
    
    public BigDecimal amount() {
    	return new BigDecimal(amount);
    }
}
