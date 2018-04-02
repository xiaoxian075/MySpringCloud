package com.scd.sdk.util.pojo;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdlistAcceptMsg extends BaseAcceptMsg {
	private List<Long> idList;

	@Override
	public boolean check() {
		if (idList == null || idList.size() == 0) {
			return false;
		}

		return true;
	}
}

