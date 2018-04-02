package com.scd.mweb.pojo.vo;

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
	private String title;
	private int type;
	private String url;
	private int hasPraise;	// 是否点赞过 0：末点赞 1：已点赞
	private long hitNum;	// 点击量
	private long praiseNum;	// 点赞量
}
