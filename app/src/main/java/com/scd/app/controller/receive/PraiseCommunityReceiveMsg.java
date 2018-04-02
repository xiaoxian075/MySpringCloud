package com.scd.app.controller.receive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PraiseCommunityReceiveMsg {

	private long id;
	private int type;
	private long praiseNum;
}
