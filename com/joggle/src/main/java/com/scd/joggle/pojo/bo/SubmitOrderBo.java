package com.scd.joggle.pojo.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitOrderBo {

	private String account;
	private String odd;
	private int type;
	private long addressId;
	private List<OrderGoodsBo> goodsList;
}
