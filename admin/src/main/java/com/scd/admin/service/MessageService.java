package com.scd.admin.service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FMessage;
import com.scd.admin.pojo.util.MessageUtil;
import com.scd.admin.pojo.vo.MessageVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.mq.MyMqSend;
import com.scd.joggle.pojo.po.MessagePo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.PushMsg;
import com.scd.sdk.util.pojo.Return;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private FMessage fMessage;

    public Return<PageInfo<MessageVo>> list(int page, int size) {
        PageInfo<MessageVo> kdPage = null;
        try {
            Return<PageInfo<MessagePo>> ret = fMessage.list(page, size);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }

            PageInfo<MessagePo> pageInfo = ret.getData();
            List<MessagePo> poList = pageInfo.getList();
            List<MessageVo> voList = MessageUtil.change(poList);

            kdPage = new PageInfo<MessageVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return Constant.createReturn(kdPage);
    }


    public Return<Long> del(long id) {
        long returnId = 0;
        try {
            Return<Long> ret = fMessage.del(id);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }
            returnId = ret.getData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn(returnId);
    }

    @Transactional
    public Return<List<Long>> batchDel(List<Long> idList) {
        List<Long> returnIdList = null;
        try {
            Return<List<Long>> ret = fMessage.batchDel(idList);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }
            returnIdList = ret.getData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn(returnIdList);
    }


    public Return<Object> add(int msgType, String msgTitle, String introduction, String msgContent) {
        try {
            Return<Object> ret = fMessage.add(msgType, msgTitle, introduction, msgContent);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn();
    }


    public Return<Object> push(long id) {
        try {
            Return<PushMsg> ret = fMessage.push(id);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }
            PushMsg pushMsg = ret.getData();
            String title = pushMsg.getTitle();
            
            if (!MyMqSend.getInstance().sendPush(id, title)) {
            	return Constant.createReturn(ErrorCom.PUSH_ERROR);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn();
    }
}
