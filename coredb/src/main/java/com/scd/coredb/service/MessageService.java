package com.scd.coredb.service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.MessageDao;
import com.scd.coredb.pojo.db.TMessage;
import com.scd.coredb.pojo.db.TPush;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.MessagePo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.PushMsg;
import com.scd.sdk.util.pojo.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public Return<PageInfo<MessagePo>> list(int page, int size) {

        Order order = new Order(Direction.DESC, TMessage.ID);
        Pageable pageable = new PageRequest(page, size, new Sort(order));
        Page<TMessage> pageInfo = messageDao.findAll(pageable);

        page = pageInfo.getNumber() + 1;
        long total = pageInfo.getTotalElements();
        List<TMessage> kdList = pageInfo.getContent();
        List<MessagePo> voList = TMessage.change(kdList);

        PageInfo<MessagePo> kdPage = new PageInfo<MessagePo>(page, total, voList);
        return Constant.createReturn(kdPage);
    }

    public Return<Long> del(long id) {
        messageDao.delete(id);
        return Constant.createReturn(id);
    }

    @Resource
	private PushService pushService;
    
    public Return<PushMsg> push(long id) {
    	TMessage tMessage = messageDao.findOne(id);
    	if (tMessage == null) {
    		return Constant.createReturn(ErrorCom.NOT_EXIST);
    	}
    	Return<TPush> retPush = pushService.insertOne(tMessage.getMsgType(), tMessage.getMsgTitle(), tMessage.getMsgContent());
    	if (Return.isErr(retPush)) {
    		return Constant.createReturn(retPush.getCode(), retPush.getDesc());
    	}
    	
    	TPush tPush = retPush.getData();
    	PushMsg pushMsg = new PushMsg(tPush.getId(), tPush.getType(), tPush.getTitle(), tPush.getContent());
        return Constant.createReturn(pushMsg);
    }

    @Transactional
    public Return<List<Long>> batchDel(List<Long> idList) {
        for (long id : idList) {
            messageDao.delete(id);
        }
        return Constant.createReturn(idList);
    }

    public Return<Object> add(int msgType, String msgTitle, String introduction, String msgContent) {
        TMessage tMessage = messageDao.findByMsgTitle(msgTitle);
        if (tMessage != null) {
            return Constant.createReturn(ErrorCom.HAS_EXIST);
        }

        long curTime = System.currentTimeMillis();
        tMessage = new TMessage(0, msgType, msgTitle, introduction, msgContent, 0, 0, curTime);
        messageDao.save(tMessage);

        return Constant.createReturn();
    }

    public Return<MessagePo> getMessageById(long id) {
        TMessage tMessage =  messageDao.findOne(id);
        MessagePo po = TMessage.createPo(tMessage);
        return Constant.createReturn(po);
    }
}
