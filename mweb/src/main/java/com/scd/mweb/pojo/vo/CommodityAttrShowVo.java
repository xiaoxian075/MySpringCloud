package com.scd.mweb.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityAttrShowVo {
	private long id;
	private String showPic;		//显示图片
	private BigDecimal price;	//价格
	private long stockNum;		//库存数量
	private List<CommodityAttrVo> attrList;
}
