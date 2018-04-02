package com.scd.mweb.hystrix;

import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CommentPo;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.feign.FComment;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018-03-05.
 */
@Component
public class HComment implements FComment {

	@Override
	public Return<PageInfo<CommentPo>> getAppCommentList(int page, int size, long goodsId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}
}
