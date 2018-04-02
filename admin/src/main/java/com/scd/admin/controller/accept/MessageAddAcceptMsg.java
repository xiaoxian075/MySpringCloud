package com.scd.admin.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageAddAcceptMsg extends BaseAcceptMsg {

    private long id;
    private int msgType;
    private String msgTitle;
    private String introduction;
    private String msgContent;
    private int push;
    private int del;
    private long createTime;

    @Override
    public boolean check() {
        if (id < 0 ) {
            return false;
        }
        return true;
    }

}
