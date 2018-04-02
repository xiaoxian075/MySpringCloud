package com.scd.app.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrolleyEditNumAcceptMsg extends BaseAcceptMsg {

	private long id;
	private int num;
	
	@Override
	public boolean check() {
		if (	id <= 0 ||
				num <= 0) {
			return false;
		}
		
		return true;
	}

}

