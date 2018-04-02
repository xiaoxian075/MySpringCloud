package com.scd.coredb.pojo.db;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_trolley")
public class TTrolley {

	public final static String ID = "id";
	public final static String ODD = "odd";
	public final static String ACCOUNT = "account";		//账号
	public final static String COMMUDITY_ID = "commudityId";	//商品ID
	public final static String TITLE = "title";		//名称
	public final static String SHOW_PIC = "showPic";	//图片URL
	public final static String ATTR_ID = "attrId";		//属性ID
	public final static String ATTR_NAME = "attrName";	//属性名称
	public final static String SHOP_ID = "shopId";		//店铺ID
	public final static String SHOP_NAME = "shopName";	//店铺名称
	public final static String PRICE = "price";			//价格
	public final static String NUM = "num";				//购买数量
	public final static String CREATE_TIME = "createTime";	//创建时间
	public final static String UPDATE_TIME = "updateTime";	//更新时间
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	@Column(name = "odd", nullable = false, length = 32)
	private String odd;
	
	@Column(name = "account", nullable = false, length = 64)
	private String account;
	
	@Column(name = "commudity_id", nullable = false, length = 20)
	private long commudityId;
	
	@Column(name = "title", nullable = false, length = 255)
	private String title;
	
	@Column(name = "show_pic", nullable = false, length = 255)
	private String showPic;
	
	@Column(name = "attr_id", nullable = false, length = 20)
	private long attrId;
	
	@Column(name = "attr_name", nullable = false, length = 64)
	private String attrName;
	
	@Column(name = "shop_id", nullable = false, length = 20)
	private long shopId;
	
	@Column(name = "shop_name", nullable = false, length = 64)
	private String shopName;
	
	@Digits(integer=11, fraction=2)
	@Column(name = "price"/*, nullable = false, length = 16*/)
	private BigDecimal price;
	
	@Column(name = "num", nullable = false, length = 11)
	private int num;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;
}