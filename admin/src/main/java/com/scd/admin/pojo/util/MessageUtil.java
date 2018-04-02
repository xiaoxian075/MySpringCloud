package com.scd.admin.pojo.util;

import com.scd.admin.pojo.vo.MessageVo;
import com.scd.joggle.pojo.po.MessagePo;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {

    public static List<MessageVo> change(List<MessagePo> poList) {
        List<MessageVo> voList = new ArrayList<MessageVo>();
        if (poList == null) {
            return voList;
        }

        for (MessagePo po : poList) {
            MessageVo vo = change(po);
            if (vo != null) {
                voList.add(vo);
            }
        }

        return voList;
    }

    private static MessageVo change(MessagePo po) {
        if (po == null) {
            return null;
        }
        return new MessageVo(po.getId(), po.getMsgType(), po.getMsgTitle(), po.getIntroduction(), po.getMsgContent(), po.getPush(), po.getDel(), po.getCreateTime());
    }

}
