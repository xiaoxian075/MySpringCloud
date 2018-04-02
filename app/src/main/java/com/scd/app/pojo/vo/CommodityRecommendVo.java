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
public class CommodityRecommendVo {
	private long id;
	private String shortTitle;	//简洁标题
	private String showPic;		//默认显示图片
	private long allSaleNum;	//已售数量
	private BigDecimal price;	//价格
}
