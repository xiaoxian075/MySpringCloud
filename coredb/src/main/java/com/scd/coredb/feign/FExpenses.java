package com.scd.coredb.feign;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.ExpensesDao;
import com.scd.coredb.pojo.db.TExpenses;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.ExpensesParam;
import com.scd.joggle.pojo.po.ExpensesPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/expenses")
public class FExpenses {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExpensesDao expensesDao;

    @RequestMapping(value = "/list")
    public Return<PageInfo<ExpensesPo>> list(int page, int size) {
        PageInfo<ExpensesPo> kdPage = null;
        try {
            Pageable pageable = new PageRequest(page, size);
            Page<TExpenses> pageInfo = expensesDao.findAll(pageable);

            page = pageInfo.getNumber() + 1;
            long total = pageInfo.getTotalElements();
            List<TExpenses> kdList = pageInfo.getContent();
            List<ExpensesPo> voList = TExpenses.change(kdList);

            kdPage = new PageInfo<ExpensesPo>(page, total, voList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return Constant.createReturn(kdPage);
    }

    @RequestMapping(value = "/del")
    public Return<Long> del(long id) {
        try {
            expensesDao.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn(id);
    }

    @Transactional
    @RequestMapping(value = "/batchDel")
    public Return<List<Long>> batchDel(@RequestBody List<Long> idList) {

        try {
            for (long id : idList) {
                expensesDao.delete(id);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return Constant.createReturn(idList);
    }

    @RequestMapping(value = "/add")
    public Return<Object> add(@RequestBody ExpensesParam param) {
        TExpenses tExpenses = null;
        try {
            long curTime = System.currentTimeMillis();
            tExpenses = new TExpenses(0, param.getBullAddrId(), param.getBullAddrName(), param.getCommodityAddrId(), param.getCommodityAddrName(), param.getPrice(), curTime, curTime);
            expensesDao.save(tExpenses);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn();
    }

    @RequestMapping(value = "/edit")
    public Return<Object> edit(@RequestBody ExpensesParam param) {
        TExpenses tExpenses = null;
        try {
            tExpenses = expensesDao.findOne(param.getId());
            if (tExpenses == null) {
                return Constant.createReturn(ErrorCom.EXPENSES_NOT_EXIST);
            }

            boolean isUpdate = false;
            if (!(param.getBullAddrId() == tExpenses.getBullAddrId())) {
                tExpenses.setBullAddrId(param.getBullAddrId());
                isUpdate = true;
            }
            if (!param.getBullAddrName().equals(tExpenses.getBullAddrName())) {
                tExpenses.setBullAddrName(param.getBullAddrName());
                isUpdate = true;
            }
            if (!(param.getCommodityAddrId() == tExpenses.getCommodityAddrId())) {
                tExpenses.setCommodityAddrId(param.getCommodityAddrId());
                isUpdate = true;
            }
            if (!param.getCommodityAddrName().equals(tExpenses.getCommodityAddrName())) {
                tExpenses.setCommodityAddrName(param.getCommodityAddrName());
                isUpdate = true;
            }
            if (!param.getPrice().equals(tExpenses.getPrice())) {
                tExpenses.setPrice(param.getPrice());
                isUpdate = true;
            }
            if (!isUpdate) {
                return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
            }

            tExpenses.setUpdateTime(System.currentTimeMillis());
            expensesDao.save(tExpenses);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn();
    }
}
