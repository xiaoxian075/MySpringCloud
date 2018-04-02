package com.scd.app.pojo.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopTrolleyVo {
	
	private long shopId;
	private String shopName;
	private List<TrolleyVo> commodityList;
	
	public void addVo(TrolleyVo vo) {
		if (vo != null) {
			commodityList.add(vo);
		}
	}
}
