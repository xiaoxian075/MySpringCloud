package com.scd.admin.pojo.util;

import com.scd.admin.pojo.vo.PayRecordVo;
import com.scd.joggle.pojo.po.PayRecordPo;

import java.util.ArrayList;
import java.util.List;

public class PayRecordUtil {

    public static List<PayRecordVo> change(List<PayRecordPo> poList) {
        if (poList == null) {
            return null;
        }
        List<PayRecordVo> voList = new ArrayList<PayRecordVo>();
        for (PayRecordPo po : poList) {
            PayRecordVo vo = change(po);
            if (vo != null) {
                voList.add(vo);
            }
        }
        return voList;
    }

    private static PayRecordVo change(PayRecordPo po) {
        if (po == null) {
            return null;
        }

        return new PayRecordVo(
                po.getId(),
                po.getTradeOdd(),
                po.getOrderOdd(),
                po.getAct(),
                po.getPayWay(),
                po.getOrderType(),
                po.getPayAccount(),
                po.getReceiveAccount(),
                po.getAmount(),
                po.getTradeTime(),
                po.getCreateTime()
        );
    }
}
