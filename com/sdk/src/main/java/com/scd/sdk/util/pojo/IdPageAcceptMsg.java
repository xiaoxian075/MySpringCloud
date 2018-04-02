package com.scd.sdk.util.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdPageAcceptMsg extends BasePageAcceptMsg {
	
	private long id;

	@Override
	public boolean check() {
		if (id < 0) {
			return false;
		}
		
		page = getPage(page);
		size = getSize(size);

		return true;
	}

}
