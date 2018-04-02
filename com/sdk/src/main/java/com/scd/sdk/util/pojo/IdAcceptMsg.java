package com.scd.sdk.util.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdAcceptMsg extends BaseAcceptMsg {
	
	private long id;

	@Override
	public boolean check() {
		if (id <= 0) {
			return false;
		}

		return true;
	}

}
