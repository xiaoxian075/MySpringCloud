package com.scd.admin.dao.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_admin")
public class TAdmin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	/**
	 * 登入名
	 */
	@Column(name = "login_name", nullable = false, length = 64)
	private String loginName;
	
	/**
	 * 密码
	 */
	@Column(name = "password", nullable = false, length = 255)
	private String password;
	
	/**
	 * 用户名
	 */
	@Column(name = "user_name", nullable = false, length = 64)
	private String userName;
	
	/**
	 * 头像URL
	 */
	@Column(name = "head_url", nullable = false, length = 255)
	private String headUrl;
	
	/**
	 * 年龄
	 */
	@Column(name = "age", nullable = false, length = 4)
	private int age;
	
	/**
	 * 说明
	 */
	@Column(name = "desc", nullable = false, length = 255)
	private String desc;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	/**
	 * 更新时间
	 */
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;
}
