package com.scd.coredb.pojo.db;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_platform_balance")
public class TPlatformBalance {

    public final static String ID = "id";
    public final static String ACCOUNT = "account";
    public final static String BALANCE = "balance";
    public final static String CREATE_TIME = "createTime";
    public final static String UPDATE_TIME = "updateTime";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, length = 20)
    private long id;

    @Column(name = "account", nullable = false, length = 64)
    private String account;

    @Digits(integer=11, fraction=2)
	@Column(name = "balance")
	private BigDecimal balance;

    @Column(name = "create_time", nullable = false, length = 20)
    private long createTime;

    @Column(name = "update_time", nullable = false, length = 20)
    private long updateTime;
}
