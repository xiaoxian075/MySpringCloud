package com.scd.sdk.util.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class H5IdAcceptMsg extends H5BaseAcceptMsg {
	
	private long id;

	@Override
	public boolean check() {
		if (id <= 0) {
			return false;
		}

		return true;
	}

}
