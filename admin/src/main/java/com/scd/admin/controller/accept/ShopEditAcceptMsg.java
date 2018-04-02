package com.scd.admin.controller.accept;

import java.util.List;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopEditAcceptMsg extends BaseAcceptMsg {

    private long id;
    private String name;
    private List<String> listPic;

    @Override
    public boolean check() {
        if (id <= 0 ||
                name == null || name.trim().length() == 0 || name.trim().length() > 64) {
            return false;
        }
        return true;
    }

}
