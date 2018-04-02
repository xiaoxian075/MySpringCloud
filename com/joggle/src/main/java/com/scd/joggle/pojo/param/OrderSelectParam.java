package com.scd.joggle.pojo.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSelectParam {
	private int page;
	private int size;
	private String account;
	private String odd;
	private int state;
}
