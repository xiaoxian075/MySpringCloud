package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BasePageAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-13.
 */
@Getter
@Setter
@AllArgsConstructor
public class AccountAcceptMsg extends BasePageAcceptMsg {

    private String account;

    @Override
    public boolean check() {

        page = getPage(page);
        size = getSize(size);
        return true;
    }
}
