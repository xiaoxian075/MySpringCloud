package com.scd.app.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.app.pojo.vo.AddressVo;
import com.scd.joggle.pojo.po.AddressPo;

public class AddressUtil {

	public static List<AddressVo> change(List<AddressPo> addressList) {
			if (addressList == null) {
				return null;
			}

			List<AddressVo> voList = new ArrayList<AddressVo>();
			for (AddressPo po : addressList) {
				voList.add(change(po));
			}
			
			return voList;
		

	}

	public static AddressVo change(AddressPo po) {
		if (po == null) {
			return null;
		}
		return new AddressVo(
				po.getId(), 
				po.getAccount(),
				po.getName(),
				po.getPhone(),
				po.getAddrId(),
				po.getAddrName(),
				po.getAddrDetail(),
				po.getIsDefault());
	}
}
