package com.scd.app.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-03-13.
 */
@Getter
@Setter
public class AccountInfoVo {
    private long id;
    private String account;
    private String nickName;
    private String loginName;
    private String headUrl;
    private int sex;
    private int payPassword;
    private int isPushMsg;
    private BigDecimal balance;
    private long yunBalance;
    private int level;
    private BigDecimal totalEarnings;

    public AccountInfoVo() {
    }

    public AccountInfoVo(long id, String account, String nickName, String loginName, String headUrl, int sex, int payPassword, int isPushMsg, BigDecimal balance, long yunBalance, int level) {
        this.id = id;
        this.account = account;
        this.nickName = nickName;
        this.loginName = loginName;
        this.headUrl = headUrl;
        this.sex = sex;
        this.payPassword = payPassword;
        this.isPushMsg = isPushMsg;
        this.balance = balance;
        this.yunBalance = yunBalance;
        this.level = level;
    }

    public AccountInfoVo(long id, String account, String nickName, String loginName, String headUrl, int sex, int level) {
        this.id = id;
        this.account = account;
        this.nickName = nickName;
        this.loginName = loginName;
        this.headUrl = headUrl;
        this.sex = sex;
        this.level = level;
    }

    public AccountInfoVo( String account,BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
        this.account = account;
    }

    public AccountInfoVo(BigDecimal balance) {
        this.balance = balance;
    }
}
