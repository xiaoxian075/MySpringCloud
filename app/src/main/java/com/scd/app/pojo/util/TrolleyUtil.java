package com.scd.app.pojo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scd.app.pojo.vo.ShopTrolleyVo;
import com.scd.app.pojo.vo.TrolleyVo;
import com.scd.joggle.pojo.po.TrolleyPo;

public class TrolleyUtil {

	public static List<ShopTrolleyVo> change(List<TrolleyPo> poList) {
		if (poList == null) {
			return null;
		}
		
		List<ShopTrolleyVo> voList = new ArrayList<ShopTrolleyVo>();
		Map<Long, ShopTrolleyVo> mapData = new HashMap<Long, ShopTrolleyVo>();
		for (TrolleyPo po : poList) {
			TrolleyVo vo = change(po);
			if (vo == null) {
				continue;
			}
			long shopId = po.getShopId();
			ShopTrolleyVo shopVo = mapData.get(shopId);
			if (shopVo == null) {
				List<TrolleyVo> trolleyList = new ArrayList<TrolleyVo>();
				shopVo = new ShopTrolleyVo(shopId, po.getShopName(), trolleyList);
				mapData.put(shopId, shopVo);
				voList.add(shopVo);
			}
			
			shopVo.addVo(vo);
		}
		return voList;
	}

	public static TrolleyVo change(TrolleyPo po) {
		if (po == null) {
			return null;
		}
		return new TrolleyVo(
				po.getId(),
				/*po.getAccount(),*/
				po.getCommudityId(),
				po.getTitle(),
				po.getShowPic(),
				po.getAttrId(),
				po.getAttrName(),
/*				po.getShopId(),
				po.getShopName(),*/
				po.getPrice(),
				po.getNum()
				);
	}
}
