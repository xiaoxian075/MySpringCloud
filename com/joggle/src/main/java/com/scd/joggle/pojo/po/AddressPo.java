package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressPo {
	private long id;
	private String account;		// 账号
	private String name;		// 收货人姓名
	private String phone;		// 收货人联系方式
	private long addrId;		// 所在地区（区/县)ID
	private String addrName;	// 所在地区（区/县)名称
	private String addrDetail;	// 详细街道
	private int isDefault;		// 是否默认 0：不是，1：是
	private long createTime;
	private long updateTime;
}
