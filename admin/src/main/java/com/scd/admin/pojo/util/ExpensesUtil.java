package com.scd.admin.pojo.util;

import com.scd.admin.controller.accept.ExpensesAcceptMsg;
import com.scd.admin.pojo.vo.ExpensesVo;
import com.scd.joggle.pojo.param.ExpensesParam;
import com.scd.joggle.pojo.po.ExpensesPo;

import java.util.ArrayList;
import java.util.List;

public class ExpensesUtil {

    public static List<ExpensesVo> change(List<ExpensesPo> poList) {
        List<ExpensesVo> voList = new ArrayList<ExpensesVo>();
        if (poList == null) {
            return voList;
        }

        for (ExpensesPo po : poList) {
            ExpensesVo vo = change(po);
            if (vo != null) {
                voList.add(vo);
            }
        }

        return voList;
    }

    public static ExpensesVo change(ExpensesPo po) {
        if (po == null) {
            return null;
        }
        return new ExpensesVo(po.getId(), po.getBullAddrId(), po.getBullAddrName(), po.getCommodityAddrId(), po.getCommodityAddrName(), po.getPrice(), po.getCreateTime(), po.getUpdateTime());
    }

    public static ExpensesParam change(ExpensesAcceptMsg acceptMsg) {
        if (acceptMsg == null) {
            return null;
        }

        return new ExpensesParam(
                acceptMsg.getId(),
                acceptMsg.getBullAddrId(),
                acceptMsg.getBullAddrName(),
                acceptMsg.getCommodityAddrId(),
                acceptMsg.getCommodityAddrName(),
                acceptMsg.getPrice(),
                acceptMsg.getCreateTime(),
                acceptMsg.getUpdateTime());
    }
}

