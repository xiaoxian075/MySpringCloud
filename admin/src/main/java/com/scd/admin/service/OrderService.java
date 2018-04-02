package com.scd.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FOrder;
import com.scd.admin.pojo.util.OrderUtil;
import com.scd.admin.pojo.vo.OrderVo;
import com.scd.joggle.pojo.param.OrderSelectParam;
import com.scd.joggle.pojo.po.OrderPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class OrderService {
	
	@Autowired
	private FOrder fOrder;

	public Return<PageInfo<OrderVo>> list(OrderSelectParam param) {

        Return<PageInfo<OrderPo>> ret = fOrder.getPage(param);
        if (Return.isErr(ret)) {
            return Constant.createReturn(ret.getCode(), ret.getDesc());
        }

        PageInfo<OrderPo> pageInfo = ret.getData();
        List<OrderPo> poList = pageInfo.getList();
        List<OrderVo> voList = OrderUtil.change(poList);

        PageInfo<OrderVo> kdPage = new PageInfo<OrderVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);

        return Constant.createReturn(kdPage);
	}

	public Return<Object> sendGoods(String orderOdd, String code, String expOdd) {
		return fOrder.sendGoods(orderOdd, code, expOdd);
	}

}
