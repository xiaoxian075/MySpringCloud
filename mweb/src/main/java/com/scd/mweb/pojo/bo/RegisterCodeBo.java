package com.scd.mweb.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterCodeBo {
	private int type;
	private String phone;
	private String code;
	private long timestamp;
}
