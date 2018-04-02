package com.scd.coredb.pojo.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.Digits;

/**
 * Created by Administrator on 2018-03-13.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_collect")
public class TCollect {

    public final static String ACCOUNT = "account";
    public final static String STATE = "state";
    public final static String CREATE_TIME = "createTime";
    public final static String COMMODITY_ID = "commodityId";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, length = 20)
    private long id;

    @Column(name = "account_id", nullable = false, length = 20)
    private long accountId;

    @Column(name = "account", nullable = false, length = 20)
    private String account;

    @Column(name = "commodity_id", nullable = false, length = 20)
    private long commodityId;

    @Column(name = "create_time", nullable = false, length = 20)
    private long createTime;

    @Column(name = "state", nullable = false, length = 20)
    private int state;

    @Column(name = "commodity_name", nullable = false, length = 20)
    private String commodityName;

    @Column(name = "commodity_img", nullable = false, length = 20)
    private String commodityImg;
    
	@Digits(integer=11, fraction=2)
	@Column(name = "price"/*, nullable = false, length = 16*/)
	private BigDecimal price;

}
