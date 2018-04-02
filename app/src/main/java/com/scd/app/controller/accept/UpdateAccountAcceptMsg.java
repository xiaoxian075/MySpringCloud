package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-14.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountAcceptMsg extends BaseAcceptMsg {
    private String nickName;
    private int sex;
    private String headPic;

    @Override
    public boolean check() {
        return true;
    }
}
