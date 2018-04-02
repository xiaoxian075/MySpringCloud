package com.scd.joggle.pojo.po;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpInfoPo {
	private long commodityAddrId;
	private String commodityAddrName;
	private long bullAddrId;
	private String bullAddrName;
	private BigDecimal price;
}
