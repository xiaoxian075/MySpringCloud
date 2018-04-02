package com.scd.mweb.pojo.vo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityAssortVo {
	private long id;
	private String showPic;		//默认显示图片
	private BigDecimal price;	//价格
}
