package com.scd.joggle.pojo.bo;

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
public class AccountInfoBo {
    private long id;
    private String account;
    private String nickName;
    private String headUrl;
    private String referee;
    private int sex;
    private int level;

}
