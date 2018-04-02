package com.scd.coredb.feign;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.MessageService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.MessagePo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.PushMsg;
import com.scd.sdk.util.pojo.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/message")
public class FMesssage {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/list")
    public Return<PageInfo<MessagePo>> list(int page, int size) {
        Return<PageInfo<MessagePo>> ret = null;
        try {
            ret = messageService.list(page, size);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return ret;
    }

    @RequestMapping(value = "/getMessageById")
    public Return<MessagePo> getMessageById(long id) {
        Return<MessagePo> ret = null;
        try {
            ret = messageService.getMessageById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return ret;
    }

    @RequestMapping(value = "/del")
    public Return<Long> del(long id) {
        Return<Long> ret = null;
        try {
            ret = messageService.del(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return ret;
    }

    @RequestMapping(value = "/batchDel")
    public Return<List<Long>> batchDel(@RequestBody List<Long> idList) {
        Return<List<Long>> ret = null;
        try {
            ret = messageService.batchDel(idList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return ret;
    }

    @RequestMapping(value = "/add")
    public Return<Object> add(int msgType, String msgTitle, String introduction, String msgContent) {
        Return<Object> ret = null;
        try {
            ret = messageService.add(msgType, msgTitle, introduction, msgContent);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return ret;
    }
    
    @RequestMapping(value = "/push")
    public Return<PushMsg> push(long id) {
        Return<PushMsg> ret = null;
        try {
            ret = messageService.push(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return ret;
    }
}


