package com.scd.admin.hystrix;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FMessage;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.MessagePo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.PushMsg;
import com.scd.sdk.util.pojo.Return;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HMessage implements FMessage {


    @Override
    public Return<PageInfo<MessagePo>> list(int page, int size) {
        return Constant.createReturn(ErrorCom.FEIGN_ERROR);
    }

    @Override
    public Return<Object> add(int msgType, String msgTitle, String introduction, String msgContent) {
        return Constant.createReturn(ErrorCom.FEIGN_ERROR);
    }

    @Override
    public Return<Long> del(long id) {
        return Constant.createReturn(ErrorCom.FEIGN_ERROR);
    }

//    @Override
//    public Return<Long> push(long id) {
//        return Constant.createReturn(ErrorCom.FEIGN_ERROR);
//    }

    @Override
    public Return<List<Long>> batchDel(List<Long> idList) {
        return Constant.createReturn(ErrorCom.FEIGN_ERROR);
    }

    @Override
    public Return<MessagePo> getMessageById(long id) {
        return Constant.createReturn(ErrorCom.FEIGN_ERROR);
    }

	@Override
	public Return<PushMsg> push(long id) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}
}
