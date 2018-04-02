package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaPo {
	private long id;		// 地区ID
	private long parentId;	// 上级ID
	private String name;	// 地区名称
	private int level;		// 地区等级  1：省 2：市 3：区/县
}
