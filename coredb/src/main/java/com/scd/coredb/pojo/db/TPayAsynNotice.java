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
@Table(name = "t_pay_asyn_notice")
public class TPayAsynNotice {
//	private long id;
//    private String tradeNo;
//    private int noticeType;
//    private int state;
//    private String payYunId;
//    private String orderId;
//    private BigDecimal realAmount;
//    private String tradeTime;
//    private long createTime;
  

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, length = 20)
    private long id;

    @Column(name = "trade_no", nullable = false, length = 64)
    private String tradeNo;
    
    @Column(name = "notice_type", nullable = false, length = 11)
    private int noticeType;
    
    @Column(name = "state", nullable = false, length = 11)
    private int state;
    
    @Column(name = "pay_yun_id", nullable = false, length = 64)
    private String payYunId;
    
    @Column(name = "order_id", nullable = false, length = 64)
    private String orderId;

    @Digits(integer=11, fraction=2)
	@Column(name = "real_amount")
	private BigDecimal realAmount;

    @Column(name = "trade_time", nullable = false, length = 32)
    private String tradeTime;

    @Column(name = "create_time", nullable = false, length = 20)
    private long createTime;
}
