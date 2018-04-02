package com.scd.admin.pojo.vo;

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
	private String shortTitle;
	private String fullTitle;
	private long shopId;
	private int shopProductType;
	private String shopName;
	private String showPic;
	private List<String> listPic;
	private long addrId;
	private String addrName;
	private String serviceItem;
	private long allSaleNum;
	private long monthSaleNum;
	private int isFreeExp;
	private int state;
	private long createTime;
	private long updateTime;
}
