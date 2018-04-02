package com.scd.coredb.pojo.db;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "t_push")
public class TPush {

	public final static String ID = "id";
	public final static String TYPE = "type";		// 类型
	public final static String TITLE = "title";		// 标题
	public final static String CONTENT = "content";	//内容
	public final static String CREATE_TIME = "createTime";
	public final static String UPDATE_TIME = "updateTime";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	@Column(name = "type", nullable = false, length = 11)
	private int type;
	
	@Column(name = "title", nullable = false, length = 64)
	private String title;
	
	/*@Lob*/@Basic(fetch=FetchType.LAZY)
	@Column(name = "content")
	private String content;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;
}
