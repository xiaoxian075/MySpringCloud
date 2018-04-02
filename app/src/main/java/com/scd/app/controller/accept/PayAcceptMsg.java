package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PayAcceptMsg extends BaseAcceptMsg {

    private String orderOdd;

    @Override
    public boolean check() {
        if(orderOdd == null || orderOdd.length() <= 0 || orderOdd.length() > 32) {
            return false;
        }
        return true;
    }
}
