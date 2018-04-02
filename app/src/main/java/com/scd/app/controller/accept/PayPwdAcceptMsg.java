package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-13.
 */
@Getter
@Setter
@AllArgsConstructor
public class PayPwdAcceptMsg extends BaseAcceptMsg {
    private String account;
    private String payPwd;
    private String oldPayPwd;

    @Override
    public boolean check() {
//        if(account == null){
//            return false;
//        }
//        if(payPwd == null){
//            return false;
//        }
        return true;
    }
}
