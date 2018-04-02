package com.scd.coredb.pojo.db;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import com.scd.joggle.pojo.po.CommodityAttrPo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_commodity_attr")
public class TCommodityAttr {
	
	public final static String ID = "id";
	public final static String COMMODITY_ID = "commodityId";	//商品ID
	public final static String ATTR_ID = "attrId";	//属性ID
	public final static String ATTR_NAME = "attrName";	//属性名称
	public final static String PRICE = "price";	//价格
	public final static String STOCK_NUM = "stockNum";	//库存数量
	public final static String CREATE_TIME = "createTime";
	public final static String UPDATE_TIME = "updateTime";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;

	@Column(name = "commodity_id", nullable = false, length = 20)
	private long commodityId;
	
	@Column(name = "attr_id", nullable = false, length = 20)
	private long attrId;
	
	@Column(name = "attr_name", nullable = false, length = 255)
	private String attrName;
	
	@Digits(integer=11, fraction=2)
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "stock_num", nullable = false, length = 20)
	private long stockNum;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;

	public CommodityAttrPo createPo() {
		return new CommodityAttrPo(
				id,
				commodityId,
				attrId,
				attrName,
				price,
				stockNum,
				createTime,
				updateTime);
	}
}

