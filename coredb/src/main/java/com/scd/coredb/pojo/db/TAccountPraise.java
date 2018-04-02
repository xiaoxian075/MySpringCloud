package com.scd.coredb.pojo.db;

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
@Table(name = "t_account_praise")
public class TAccountPraise {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	/**
	 * 账号
	 */
	@Column(name = "account", nullable = false, length = 64)
	private String account;
	
	/**
	 * 社区ID
	 */
	@Column(name = "community_id", nullable = false, length = 20)
	private long communityId;
	
	/**
	 * 状态 0：无点赞 1：点赞
	 */
	@Column(name = "state", nullable = false, length = 11)
	private int state;
	
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

