package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BasePageAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-01.
 */
@Getter
@Setter
@AllArgsConstructor
public class GoodsAcceptMsg extends BasePageAcceptMsg {

    private int shopId;

    @Override
    public boolean check() {
        if (shopId < 0) {
            return false;
        }
        page = getPage(page);
        size = getSize(size);
        return true;
    }
}
