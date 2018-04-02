package com.scd.admin.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopActivityAcceptMsg extends BaseAcceptMsg {

	private long id;
	private long shopId;
    private String title;

    @Override
    public boolean check() {
    	if (id < 0) {
    		return false;
    	}
        if (title == null || title.trim().length() == 0 || title.trim().length() > 64) {
            return false;
        }
        if (shopId <= 0) {
        	return false;
        }
        return true;
    }

}
