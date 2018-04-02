package com.scd.joggle.pojo.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommoditySelectParam {
	private long shopId;
	private int shopProductType;
	private int state;
	private int page;
	private int size;
}
