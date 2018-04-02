package com.scd.admin.controller.accept;

import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.BasePageAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemindPageAcceptMsg extends BasePageAcceptMsg {

	private int state;
	
	@Override
	public boolean check() {
		if (state != 0 && !Type.checkRemind(state)) {
			return false;
		}
		page = getPage(page);
		size = getSize(size);
		return true;
	}

}
