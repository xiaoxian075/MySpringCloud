package com.scd.mweb.pojo.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityVo {
	private long id;
	private String shortTitle;	//简洁标题
	private String fullTitle;	//详细标题
	private long shopId;		//店铺ID
	private String showPic;		//默认显示图片
	private List<String> listPic;	//图片集
	private long addrId;		//所在地区（城市)ID
	private String addrName;	//所在地区（城市)名称
	private String serviceItem;	//服务项目
	private long allSaleNum;	//已售数量
	private long monthSaleNum;	//月售数量
	private long stockNum;		//库存
	private int isFreeExp;		//是否免运费 0：否 1：是
	private List<CommodityAttrVo> attrList;	//属性信息
}

