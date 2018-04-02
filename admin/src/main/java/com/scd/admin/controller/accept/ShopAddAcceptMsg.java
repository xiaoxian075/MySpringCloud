package com.scd.admin.controller.accept;

import java.util.ArrayList;
import java.util.List;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopAddAcceptMsg extends BaseAcceptMsg {

    private String name;
    private List<String> listPic;

    @Override
    public boolean check() {
        if (name == null || name.trim().length() == 0 || name.trim().length() > 64) {
            return false;
        }
        if (listPic == null) {
        	listPic = new ArrayList<String>();
        }
        return true;
    }

}
