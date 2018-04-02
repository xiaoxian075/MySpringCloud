package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PayResultAcceptMsg extends BaseAcceptMsg{

    private String tradeOdd;
    @Override
    public boolean check() {
        if(tradeOdd == null || tradeOdd.length() == 0 || tradeOdd.length() > 32){
            return false;
        }
        return true;
    }
}
