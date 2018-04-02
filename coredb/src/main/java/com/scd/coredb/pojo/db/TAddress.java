package com.scd.coredb.pojo.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scd.joggle.pojo.bo.AddressBo;
import com.scd.joggle.pojo.po.AddressPo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_address")
public class TAddress {
	
	public final static String ACCOUNT = "account";
	public final static String IS_DEFAULT = "isDefault";
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;

	@Column(name = "account", nullable = false, length = 64)
	private String account;
	
	@Column(name = "name", nullable = false, length = 64)
	private String name;
	
	@Column(name = "phone", nullable = false, length = 32)
	private String phone;

	@Column(name = "addr_id", nullable = false, length = 20)
	private long addrId;

	@Column(name = "addr_name", nullable = false, length = 255)
	private String addrName;
	
	@Column(name = "addr_detail", nullable = false, length = 255)
	private String addrDetail;
	
	@Column(name = "is_default", nullable = false, length = 11)
	private int isDefault;
	
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;

	public AddressPo createPo() {
		return new AddressPo(id, account, name, phone, addrId, addrName, addrDetail, isDefault, createTime, updateTime);
	}
	
	public static TAddress createT(AddressPo po) {
		return new TAddress(po.getId(), po.getAccount(), po.getName(), po.getPhone(), po.getAddrId(), po.getAddrName(), po.getAddrDetail(), po.getIsDefault(), po.getCreateTime(), po.getUpdateTime());
	}

	public static List<AddressPo> change(List<TAddress> addressList) {
		if (addressList == null) {
			return null;
		}
		List<AddressPo> poList = new ArrayList<AddressPo>();
    	for (TAddress tAddress : addressList) {
    		poList.add(tAddress.createPo());
    	}
    	return poList;
	}

	public AddressBo createBo() {
		return new AddressBo(
				name,
				phone,
				addrId,
				addrName,
				addrDetail);
	}
}
