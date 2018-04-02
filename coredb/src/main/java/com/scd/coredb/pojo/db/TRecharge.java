package com.scd.coredb.pojo.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-03-08.
 */
@Getter
@Setter
@Entity
@Table(name = "t_recharge")
public class TRecharge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, length = 20)
    private long id;

    @Column(name = "account_id", nullable = false, length = 20)
    private long accountId;

    @Column(name = "account", nullable = false, length = 20)
    private String account;

    @Column(name = "amount", nullable = false, length = 20)
    private BigDecimal amount;

    @Column(name = "odd", nullable = false, length = 20)
    private String odd;

    @Column(name = "cretae_time", nullable = false, length = 20)
    private long cretaeTime;

    public TRecharge() {
    }

    public TRecharge(long accountId, String account, BigDecimal amount, long cretaeTime, String odd) {
        this.accountId = accountId;
        this.account = account;
        this.amount = amount;
        this.cretaeTime = cretaeTime;
        this.odd = odd;
    }
}
