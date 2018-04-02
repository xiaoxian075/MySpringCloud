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
@Table(name = "t_push_state")
public class TPushState {

	public final static String ID = "id";
	public final static String ACCOUNT = "account";		// 类型
	public final static String PUSH_ID = "pushId";		// 标题
	public final static String STATE = "state";	//状态  0：末读 1：已读
	public final static String CREATE_TIME = "createTime";
	public final static String UPDATE_TIME = "updateTime";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	@Column(name = "account", nullable = false, length = 64)
	private String account;
	
	@Column(name = "push_id", nullable = false, length = 20)
	private long pushId;
	
	@Column(name = "state", nullable = false, length = 11)
	private int state;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;
}
