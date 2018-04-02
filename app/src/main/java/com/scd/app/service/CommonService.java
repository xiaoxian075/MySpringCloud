package com.scd.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FCommon;
import com.scd.app.feign.FShop;
import com.scd.app.pojo.util.CommonUtil;
import com.scd.app.pojo.vo.AreaVo;
import com.scd.app.pojo.vo.ExpInfoVo;
import com.scd.app.pojo.vo.ExpMulInfoVo;
import com.scd.app.pojo.vo.ShopVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.joggle.pojo.po.AreaPo;
import com.scd.joggle.pojo.po.ShopPo;
import com.scd.sdk.util.pojo.Return;

@Service
public class CommonService {
	
	@Autowired
	private FCommon fCommon;
	
	@Autowired
	private FShop fShop;

	public Return<List<AreaShortPojo>> getAreaChild(long parentId) {
		Return<List<AreaShortPojo>> ret = fCommon.getAreaChild(parentId);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		return ret;
	}

	public Return<List<AreaVo>> getArea() {
		Return<List<AreaPo>> ret = fCommon.getAllArea();
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		Map<Long, AreaVo> poMap = new HashMap<Long, AreaVo>();
		List<AreaVo> voList = new ArrayList<AreaVo>();
		List<AreaPo> poList = ret.getData();
		for (AreaPo po: poList) {
			AreaVo vo = new AreaVo(po.getId(), po.getName(), po.getLevel(), new ArrayList<AreaVo>());
			long id = po.getParentId();
			int level = po.getLevel();
			if (level == 1) {
				voList.add(vo);
				poMap.put(vo.getId(), vo);
			} else if (level == 2 || level == 3) {
				AreaVo parentVo = poMap.get(id);
				if (parentVo != null) {
					parentVo.addChild(vo);
					if (level == 2) {
						poMap.put(vo.getId(), vo);
					}
				}
			}
			
		}
		
		
		return Constant.createReturn(voList);
	}
	
	public Return<ExpInfoVo> getExp(long commodityId, long addressId) {
		Return<BigDecimal> ret = fCommon.getExp(commodityId, addressId);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		ExpInfoVo vo = new ExpInfoVo(ret.getData());
		return Constant.createReturn(vo);
	}
	
	public Return<ExpMulInfoVo> getMulExp(List<List<Long>> commodityIdList, long addressId) {
		Return<List<BigDecimal>> ret = fCommon.getMulExp(commodityIdList, addressId);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		ExpMulInfoVo vo = new ExpMulInfoVo(ret.getData());
		return Constant.createReturn(vo);
	}

	public Return<ShopVo> getShopPics(long shopId) {
		Return<ShopPo> ret = fShop.getShop(shopId);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		ShopVo vo = CommonUtil.change(ret.getData());
		if (vo == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(vo);
	}
}
