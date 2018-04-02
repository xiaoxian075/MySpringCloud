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
public class MenuVo {
	private String title;
	private String path;
	private String icon;
	private List<MenuVo> child;
}
