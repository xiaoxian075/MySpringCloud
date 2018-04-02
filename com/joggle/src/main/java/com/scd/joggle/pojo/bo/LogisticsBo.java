package com.scd.joggle.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsBo {

	private String code;	// 快递简码
	private String name;	// 快递名称
	private String expOdd;	// 物流订单号
}
