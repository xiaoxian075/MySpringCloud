package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityPo {

	private long id;
	private String shortTitle;
	private String fullTitle;
	private long shopId;
	private String shopName;
	private String showPic;
	private List<String> listPic;
	private long addrId;
	private String addrName;
	private String serviceItem;
	private long allSaleNum;
	private long monthSaleNum;
	private int isFreeExp;
	private long createTime;
	private long updateTime;
	private List<CommodityAttrPo> attrList;
}
