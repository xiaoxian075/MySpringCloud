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
@Table(name = "t_menu")
public class TMenu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	/**
	 * 父ID
	 */
	@Column(name = "parent_id", nullable = false, length = 20)
	private long parentId;
	
	/**
	 * 名称（组件对应名称）
	 */
	@Column(name = "name", nullable = false, length = 64)
	private String name;
	
	/**
	 * 标题（导航显示名称）
	 */
	@Column(name = "title", nullable = false, length = 64)
	private String title;
	
	/**
	 * 路径
	 */
	@Column(name = "path", nullable = false, length = 32)
	private String path;
	
	/**
	 * 图标
	 */
	@Column(name = "icon", nullable = false, length = 16)
	private String icon;
	
	/**
	 * 图标
	 */
	@Column(name = "is_show", nullable = false, length = 11)
	private int isShow;
	
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
