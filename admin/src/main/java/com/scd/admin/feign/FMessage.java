package com.scd.admin.feign;

import com.scd.admin.hystrix.HMessage;
import com.scd.joggle.pojo.po.MessagePo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.PushMsg;
import com.scd.sdk.util.pojo.Return;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "${feign.coredb}", fallback = HMessage.class)
public interface FMessage {

    @RequestMapping(value = "/message/list", method = RequestMethod.POST)
    Return<PageInfo<MessagePo>> list(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size);

    @RequestMapping(value = "/message/add")
    Return<Object> add(@RequestParam(value = "msgType") int msgType, @RequestParam(value = "msgTitle") String msgTitle, @RequestParam(value = "introduction") String introduction, @RequestParam(value = "msgContent") String msgContent);

    @RequestMapping(value = "/message/del")
    Return<Long> del(@RequestParam(value = "id") long id);

    @RequestMapping(value = "/message/batchDel")
    Return<List<Long>> batchDel(@RequestBody List<Long> idList);

    @RequestMapping(value = "/message/getMessageById")
    Return<MessagePo> getMessageById(@RequestParam(value = "id")long id);

    @RequestMapping(value = "/message/push")
    Return<PushMsg> push(@RequestParam(value = "id") long id);
}
