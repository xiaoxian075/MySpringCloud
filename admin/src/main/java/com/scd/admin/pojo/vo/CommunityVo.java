package com.scd.admin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityVo {
	private long id;
	
	/**
	 * 标题
	 */
	private String title;

	/**
	 * 类型 1：学习教育 2：美食分享 3：生活健康 4：健身健美
	 */
	private int type;
	
	/**
	 * url
	 */
	private String url;
	
	/**
	 * 点击量
	 */
	private long hitNum;
	
	/**
	 * 点赞量
	 */
	private long praiseNum;
}
