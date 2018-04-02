package com.scd.sdk.util.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PushMsg {
	private long id;
	private int type;
	private String title;
	private String content;
}
