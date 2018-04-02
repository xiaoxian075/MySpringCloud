package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginPo {

	private long id;
	
	/**
	 * 登入名
	 */
	private String loginName;
	
	/**
	 * 登入密码
	 */
	private String password;
	
	/**
	 * 账号
	 */
	private String account;
	
	/**
	 * 最后一次登入时间
	 */
	private long lastLoginTime;
	
	/**
	 * 创建时间
	 */
	private long createTime;
	
	/**
	 * 更新时间
	 */
	private long updateTime;
	
}

