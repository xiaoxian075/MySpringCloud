package com.scd.joggle.pojo.po;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrolleyPo {

	private long id;
	private String odd;
	private String account;
	private long commudityId;
	private String title;
	private String showPic;
	private long attrId;
	private String attrName;
	private long shopId;
	private String shopName;
	private BigDecimal price;
	private int num;
	private long createTime;
	private long updateTime;
}
