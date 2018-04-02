package com.scd.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FShopActivity;
import com.scd.admin.pojo.util.ShopActivityUtil;
import com.scd.admin.pojo.vo.ShopActivityVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopActivityPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class ShopActivityService {
	
	@Autowired
	private FShopActivity fShopActivity;

	public Return<PageInfo<ShopActivityVo>> list(int page, int size, long shopId) {
		Return<PageInfo<ShopActivityPo>> ret = fShopActivity.list(page, size, shopId);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
        PageInfo<ShopActivityPo> pageInfo = ret.getData();
        List<ShopActivityPo> poList = pageInfo.getList();
        List<ShopActivityVo> voList = ShopActivityUtil.change(poList);

        PageInfo<ShopActivityVo> kdPage = new PageInfo<ShopActivityVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);
		
		return Constant.createReturn(kdPage);
	}

	public Return<ShopActivityVo> add(long shopId, String title) {
		Return<ShopActivityPo> ret = fShopActivity.add(shopId, title);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		ShopActivityVo vo = ShopActivityUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(vo);
	}

	public Return<ShopActivityVo> edit(long id, long shopId, String title) {
		Return<ShopActivityPo> ret = fShopActivity.edit(id, shopId, title);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		ShopActivityVo vo = ShopActivityUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(vo);
	}

	public Return<Long> del(long id) {
		return fShopActivity.del(id);
	}

	public Return<List<Long>> batchDel(List<Long> idList) {
		return fShopActivity.batchDel(idList);
	}

}
