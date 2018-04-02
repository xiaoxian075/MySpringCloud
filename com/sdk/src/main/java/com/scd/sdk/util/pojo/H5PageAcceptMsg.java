package com.scd.sdk.util.pojo;


public class H5PageAcceptMsg extends H5BasePageAcceptMsg {
	
	
	@Override
	public boolean check() {
		page = getPage(page);
		size = getSize(size);
		return true;
	}

}
