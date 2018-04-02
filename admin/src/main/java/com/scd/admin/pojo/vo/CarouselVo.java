package com.scd.admin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarouselVo {
	private long id;

	/**
	 * 类型 1：云社区 2：零距离
	 */
	private int type;
	
	/**
	 * url
	 */
	private String url;
	
}
