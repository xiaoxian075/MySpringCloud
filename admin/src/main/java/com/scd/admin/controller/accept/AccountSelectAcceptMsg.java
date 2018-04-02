package com.scd.admin.controller.accept;

import com.scd.sdk.util.pojo.BasePageAcceptMsg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSelectAcceptMsg extends BasePageAcceptMsg {
	private String account;
	
	@Override
	public boolean check() {
		page = getPage(page);
		size = getSize(size);
		if (account != null) {
			if (account.length() == 0) {
				account = null;
			} else if (account.length() > 64) {
				return false;
			}
		}
		return true;
	}
}

