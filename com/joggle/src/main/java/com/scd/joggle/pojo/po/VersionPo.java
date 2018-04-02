package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VersionPo {
	private long id;
	private int type;
	private String version;
	private int state;
	private long createTime;
	private long updateTime;
}
