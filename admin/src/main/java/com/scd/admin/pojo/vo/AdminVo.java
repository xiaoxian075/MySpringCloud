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
public class AdminVo {
	private String loginName;
	private String userName;
	private String headUrl;
	private int age;
	private String desc;
	private List<String> menu;
	
	private String sessionId;
	private String baseUrl;
	
	public AdminVo(String loginName, String userName, String headUrl, int age, String desc) {
		this.loginName = loginName;
		this.userName = userName;
		this.headUrl = headUrl;
		this.age = age;
		this.desc = desc;
	}
}
