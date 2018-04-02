package com.scd.app.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PushVo {
	private long id;
	private int type;
	private String title;
	private String content;
	private int state;
}
