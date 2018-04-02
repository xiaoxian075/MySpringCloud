package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-13.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePwdAcceptMsg extends BaseAcceptMsg {
    /**
     * 密码格式为字符、数字、字母任意两种组合，长度为8-20位
     */
    private String oldPassword;
    private String newPassword;


    @Override
    public boolean check() {
        if(oldPassword == null || "".equals(oldPassword)){
            return false;
        }
        if(newPassword.length() == 0){
        	return false;
        }
//        if (!GeneralValidationUtil.password(newPassword, 8, 20)) {
//            return false;
//        }
        return true;
    }

}
