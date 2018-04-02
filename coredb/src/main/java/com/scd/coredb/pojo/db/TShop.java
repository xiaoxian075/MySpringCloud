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
@Table(name = "t_shop")
public class TShop {

	public final static String ID = "id";
	public final static String NAME = "name";
	public final static String LIST_PIC = "list_pic";
	public final static String CREATE_TIME = "createTime";
	public final static String UPDATE_TIME = "updateTime";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	@Column(name = "name", nullable = false, length = 64)
	private String name;
	
	/*@Lob*/@Basic(fetch=FetchType.LAZY)
	@Column(name = "list_pic")
	private String listPic;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;

}


