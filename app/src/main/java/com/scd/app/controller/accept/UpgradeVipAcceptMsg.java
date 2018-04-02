package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-10.
 */

@Getter
@Setter
@AllArgsConstructor
public class UpgradeVipAcceptMsg  extends BaseAcceptMsg {
    private int payWay;
    private String odd;
    private String payPassWord;

    @Override
    public boolean check() {
        if(odd == null){
            return false;
        }
        if(payPassWord == null){
            return false;
        }
        return true;
    }
}
