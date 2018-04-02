package com.scd.app.pojo.vo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrolleyVo {
	private long id;
	//private String account;
	private long commudityId;
	private String title;
	private String showPic;
	private long attrId;
	private String attrName;
	//private long shopId;
	//private String shopName;
	private BigDecimal price;
	private int num;
}
