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
public class CartAcceptMsg extends BaseAcceptMsg {

    private long communityId;	// 商品ID
    private long attrId;	// 商品属性ID
    private int num;	// 商品数量

    @Override
    public boolean check() {
        if(communityId <=0 || attrId<=0 || num<=0){
            return false;
        }
        return true;
    }
}
