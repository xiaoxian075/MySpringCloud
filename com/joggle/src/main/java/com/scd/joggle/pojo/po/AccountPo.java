package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountPo {
    private long id;
    private String account;
    private String nickName;
    private String headUrl;
    private String referee;
    private int sex;
    private int accountState;


}
