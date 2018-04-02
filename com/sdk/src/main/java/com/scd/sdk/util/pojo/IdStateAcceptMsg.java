package com.scd.sdk.util.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdStateAcceptMsg extends BaseAcceptMsg {

	private long id;
	private int state;
	
	@Override
	public boolean check() {
		if (id <= 0 || state < 0) {
			return false;
		}
		return true;
	}

}
