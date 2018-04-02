package com.scd.sdk.util.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class H5BaseAcceptMsg {
	
	public abstract boolean check();
	private String token;	// 会话标识
}


