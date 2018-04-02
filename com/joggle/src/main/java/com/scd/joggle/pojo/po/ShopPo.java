package com.scd.joggle.pojo.po;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopPo {
	private long id;
	private String name;
	private List<String> listPic;
	private long createTime;
	private long updateTime;
}
