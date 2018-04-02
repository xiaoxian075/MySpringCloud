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
@Table(name = "t_version")
public class TVersion {

	public final static String ID = "id";
	public final static String TYPE = "type";
	public final static String VERSION = "version";
	public final static String STATE = "state";		//状态  0:未通过  1:已通过
	public final static String CREATE_TIME = "createTime";	//创建时间
	public final static String UPDATE_TIME = "updateTime";	//更新时间
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	@Column(name = "type", nullable = false, length = 11)
	private int type;
	
	@Column(name = "version", nullable = false, length = 32)
	private String version;
	
	@Column(name = "state", nullable = false, length = 11)
	private int state;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;
}
