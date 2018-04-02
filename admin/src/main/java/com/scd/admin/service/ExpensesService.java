package com.scd.admin.service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FExpenses;
import com.scd.admin.pojo.util.ExpensesUtil;
import com.scd.admin.pojo.vo.ExpensesVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.ExpensesParam;
import com.scd.joggle.pojo.po.ExpensesPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ExpensesService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FExpenses fExpenses;

    public Return<PageInfo<ExpensesVo>> list(int page, int size) {
        PageInfo<ExpensesVo> kdPage = null;
        try {
            Return<PageInfo<ExpensesPo>> ret = fExpenses.list(page, size);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }

            PageInfo<ExpensesPo> pageInfo = ret.getData();
            List<ExpensesPo> poList = pageInfo.getList();
            List<ExpensesVo> voList = ExpensesUtil.change(poList);

            kdPage = new PageInfo<ExpensesVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return Constant.createReturn(kdPage);
    }


    public Return<Long> del(long id) {
        long returnId = 0;
        try {
            Return<Long> ret = fExpenses.del(id);
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
            Return<List<Long>> ret = fExpenses.batchDel(idList);
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


    public Return<ExpensesVo> add(ExpensesParam param) {
        Return<Object> ret = fExpenses.add(param);
        if (Return.isErr(ret)) {
            return Constant.createReturn(ret.getCode(), ret.getDesc());
        }

//        ExpensesVo vo = ExpensesUtil.change(ret.getData());
//        if (vo == null) {
//            return Constant.createReturn(ErrorCom.CHANGE_ERROR);
//        }
        return Constant.createReturn();
    }


    public Return<Object> edit(ExpensesParam param) {
        try {
            Return<Object> ret = fExpenses.edit(param);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn();
    }

}
