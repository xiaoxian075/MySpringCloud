package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarouselPo {

	private long id;

	/**
	 * 类型 1：云社区 2：零距离
	 */
	private int type;
	
	/**
	 * url
	 */
	private String url;
	
	/**
	 * 创建时间
	 */
	private long createTime;
	
	/**
	 * 更新时间
	 */
	private long updateTime;
}
