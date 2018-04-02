package com.scd.app.hystrix;

import java.util.List;

import com.scd.sdk.util.pojo.PageInfo;
import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FComment;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CommentAddPo;
import com.scd.joggle.pojo.po.CommentPo;
import com.scd.sdk.util.pojo.Return;

/**
 * Created by Administrator on 2018-03-05.
 */
@Component
public class HComment implements FComment{

	@Override
	public Return<PageInfo<CommentPo>>   getAppCommentList(int page, int size, long goodsId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

	@Override
	public Return<Object> addComment(List<CommentAddPo> commentList) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}


}
