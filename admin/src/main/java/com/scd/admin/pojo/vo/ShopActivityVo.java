package com.scd.admin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopActivityVo {
	private long id;
	private long shopId;
	private String title;
	private long createTime;
	private long updateTime;
}
