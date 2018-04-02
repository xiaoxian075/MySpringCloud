package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PayBalanceAcceptMsg extends BaseAcceptMsg {

    private String orderOdd;
    private String payPassword;

    @Override
    public boolean check() {
        if(		orderOdd == null || orderOdd.length() == 0 || orderOdd.length() > 32 ||
        		payPassword == null || payPassword.length() == 0 || payPassword.length() > 255){
            return false;
        }
        
        return true;
    }
}
