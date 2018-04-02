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
@Table(name = "t_commodity")
public class TCommodity {
	
	public final static String ID = "id";
	public final static String SHORT_TITLE = "shortTitle";	//简洁标题
	public final static String FULL_TITLE = "fullTitle";	//详细标题
	public final static String SHOP_ID = "shopId";	//店铺ID
	public final static String SHOP_PRODUCT_TYPE = "shopProductType";	//店铺商品分类
	public final static String SHOW_PIC = "showPic";	//默认显示图片
	public final static String LIST_PIC = "listPic";	//图片集
	public final static String ADDR_ID = "addrId";	//所在地区（城市)ID
	public final static String ADDR_NAME = "addrName";	//所在地区（城市)名称
	public final static String SERVICE_ITEM = "serviceItem";	//服务项目
	public final static String ALL_SALE_NUM = "allSaleNum";	//已售数量
	public final static String MONTH_SALE_NUM = "monthSaleNum";	//月售数量
	public final static String IS_FREE_EXP = "isFreeExp";	//是否免邮 (0:不免邮 1：免邮)	
	public final static String STATE = "state";	//状态   0：下架 1：上架
	public final static String CREATE_TIME = "createTime";
	public final static String UPDATE_TIME = "updateTime";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;

	@Column(name = "short_title", nullable = false, length = 127)
	private String shortTitle;
	
	@Column(name = "full_title", nullable = false, length = 255)
	private String fullTitle;
	
	@Column(name = "shop_id", nullable = false, length = 20)
	private long shopId;
	
	@Column(name = "shop_product_type", nullable = false, length = 11)
	private int shopProductType;
	
	@Column(name = "show_pic", nullable = false, length = 255)
	private String showPic;
	
	/*@Lob*/@Basic(fetch=FetchType.LAZY)
	@Column(name = "list_pic")
	private String listPic;
	
	@Column(name = "addr_id", nullable = false, length = 20)
	private long addrId;
	
	@Column(name = "addr_name", nullable = false, length = 255)
	private String addrName;
	
	@Column(name = "service_item", nullable = false, length = 255)
	private String serviceItem;
	
	@Column(name = "all_sale_num", nullable = false, length = 20)
	private long allSaleNum;
	
	@Column(name = "month_sale_num", nullable = false, length = 20)
	private long monthSaleNum;
	
	@Column(name = "is_free_exp", nullable = false, length = 11)
	private int isFreeExp;
	
	@Column(name = "state", nullable = false, length = 11)
	private int state;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;	
}

