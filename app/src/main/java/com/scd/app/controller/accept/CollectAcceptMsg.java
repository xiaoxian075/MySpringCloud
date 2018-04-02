package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-14.
 */
@Getter
@Setter
@AllArgsConstructor
public class CollectAcceptMsg extends BaseAcceptMsg {

    private long commodityId;
    private String account;

    @Override
    public boolean check() {
        if(commodityId <= 0){
            return false;
        }
        return true;
    }
}
