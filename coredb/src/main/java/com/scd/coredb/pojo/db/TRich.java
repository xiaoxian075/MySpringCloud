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
@Table(name = "t_rich")
public class TRich {

	public final static String ID = "id";
	public final static String TYPE = "type";
	public final static String FOREIGN_ID = "foreignId";
	public final static String DATA = "data";
	public final static String LINK = "link";
	public final static String CREATE_TIME = "createTime";
	public final static String UPDATE_TIME = "updateTime";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	@Column(name = "type", nullable = false, length = 20)
	private long type;
	
	@Column(name = "foreign_id", nullable = false, length = 20)
	private long foreignId;
	
	/*@Lob*/@Basic(fetch=FetchType.LAZY)
	@Column(name = "data")
	private String data;
	
	/*@Lob*/@Basic(fetch=FetchType.LAZY)
	@Column(name = "link")
	private String link;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;
}


