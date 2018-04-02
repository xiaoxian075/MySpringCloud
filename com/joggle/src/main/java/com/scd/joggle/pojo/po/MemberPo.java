package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPo {
	private String account;
    private int level;		// 1:普通会员  2：VIP
    //private String twoDimensionCode;	// 二维码信息
    private BigDecimal totalConsume;	// 消费总额
    private BigDecimal totalRecharge;	// 
}
