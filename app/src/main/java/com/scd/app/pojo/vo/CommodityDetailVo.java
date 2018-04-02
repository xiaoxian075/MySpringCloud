package com.scd.app.pojo.vo;

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
public class CommodityDetailVo {
	private long id;
	private String fullTitle;	//详细标题
	private long shopId;		//店铺ID
	private List<String> listPic;	//图片集
	private String addrName;	//所在地区（城市)名称
	private String serviceItem;	//服务项目
	private long monthSaleNum;	//月售数量
	private int isFreeExp;		//是否免运费 0：否 1：是
	private long attrId;
	private String attrName;
	private BigDecimal price;	// 价格
}
