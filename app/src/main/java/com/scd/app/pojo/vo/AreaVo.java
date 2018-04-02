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
public class AreaVo {

	private long id;		// 地区ID
	private String name;	// 地区名称
	private int level;		// 地区等级  1：省 2：市 3：区/县
	private List<AreaVo> child;
	
	public void addChild(AreaVo vo) {
		if (child != null) {
			child.add(vo);
		}
	}
}
