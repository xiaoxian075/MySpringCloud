package com.scd.joggle.pojo.po;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-03-10.
 */
@Getter
@Setter
public class RechargePo {
    private long id;
    private long accountId;
    private String account;
    private BigDecimal amount;
    private String rechargeNumber;
    private long rechargeTime;
    private String odd;
    private int payAct;
    private int payWay;

    public RechargePo() {
    }

    public RechargePo(long accountId, String account, BigDecimal amount, String rechargeNumber, long rechargeTime, String odd, int payAct, int payWay) {

        this.accountId = accountId;
        this.account = account;
        this.amount = amount;
        this.rechargeNumber = rechargeNumber;
        this.rechargeTime = rechargeTime;
        this.odd = odd;
        this.payAct = payAct;
        this.payWay = payWay;
    }
}
