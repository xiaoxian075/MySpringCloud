package com.scd.app.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.app.pojo.vo.CommentAddVo;
import com.scd.joggle.pojo.po.CommentAddPo;

public class CommentUtil {

	public static List<CommentAddPo> changePo(List<CommentAddVo> voList) {
		if (voList == null) {
			return null;
		}
		
		List<CommentAddPo> poList = new ArrayList<CommentAddPo>();
		for (CommentAddVo vo : voList) {
			poList.add(new CommentAddPo(
					vo.getAccountId(),
					vo.getOrderId(),
					vo.getGoodsId(),
					vo.getGoodsDescribe(),
					vo.getDescribeStar(),
					vo.getContent(),
					vo.getIsAnonymous(),
					vo.getLogistics(),
					vo.getLogisticsStar(),
					vo.getService(),
					vo.getServiceStar(),
					vo.getPictureUrl(),
					vo.getAccount(),
					vo.getNickName(),
					vo.getHeadPicUrl()
					));
		}
		return poList;
	}

}
