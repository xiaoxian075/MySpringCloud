package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPo{

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
	 * 是否点赞 0：末点赞 1：已点赞
	 */
	private int hasPraise;
	
	/**
	 * 点击量
	 */
	private long hitNum;
	
	/**
	 * 点赞量
	 */
	private long praiseNum;
	
	/**
	 * 创建时间
	 */
	private long createTime;
	
	/**
	 * 更新时间
	 */
	private long updateTime;
}
