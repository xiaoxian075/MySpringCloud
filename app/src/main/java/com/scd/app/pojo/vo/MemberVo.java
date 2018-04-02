package com.scd.app.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberVo {
    private int isMember;		// 1:普通会员  2：VIP
    private String codeInfo;	// URL
    private BigDecimal totalConsume;	// 累计消费
    private BigDecimal totalRecharge;	// 累计充值
}
