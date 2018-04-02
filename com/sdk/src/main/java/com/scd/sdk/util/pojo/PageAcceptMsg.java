package com.scd.sdk.util.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageAcceptMsg extends BasePageAcceptMsg {
	
	
	@Override
	public boolean check() {
		page = getPage(page);
		size = getSize(size);
		return true;
	}
}
