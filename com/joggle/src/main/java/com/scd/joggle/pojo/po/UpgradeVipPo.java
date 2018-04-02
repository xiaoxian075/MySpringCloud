package com.scd.joggle.pojo.po;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-03-10.
 */
@Getter
@Setter
public class UpgradeVipPo {
    private long id;
    private long accountId;
    private String account;
    private BigDecimal amount;
    private String upgradeVipNumber;
    private long upgradeVipTime;
    private String odd;
    private int payAct;
    private int payWay;

    public UpgradeVipPo() {
    }

    public UpgradeVipPo(long accountId, String account, BigDecimal amount, String upgradeVipNumber, long upgradeVipTime, String odd, int payAct, int payWay) {

        this.accountId = accountId;
        this.account = account;
        this.amount = amount;
        this.upgradeVipNumber = upgradeVipNumber;
        this.upgradeVipTime = upgradeVipTime;
        this.odd = odd;
        this.payAct = payAct;
        this.payWay = payWay;
    }
}
