package com.scd.joggle.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressBo {
	private String name;		// 收货人姓名
	private String phone;		// 收货人联系方式
	private long addrId;		// 所在地区（区/县)ID
	private String addrName;	// 所在地区（区/县)名称
	private String addrDetail;	// 详细街道
	
	public String fullAddr() {
		return addrName + addrDetail;
	}
}
