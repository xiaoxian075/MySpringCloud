package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopActivityPo {
	private long id;
	private long shopId;
	private String title;
	private long createTime;
	private long updateTime;
}
