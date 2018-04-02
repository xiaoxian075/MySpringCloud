package com.scd.joggle.pojo.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityParam {
	private long id;
	private String shortTitle;
	private String fullTitle;
	private long shopId;
	private int shopProductType;
	private String showPic;
	private List<String> listPic;
	private long addrId;
	private String serviceItem;
	private int isFreeExp;
	private int state;
}
