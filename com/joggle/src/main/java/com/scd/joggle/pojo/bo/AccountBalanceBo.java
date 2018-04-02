package com.scd.joggle.pojo.bo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceBo {
	private String account;
	private BigDecimal balance;
	private BigDecimal totalEarnings;
}
