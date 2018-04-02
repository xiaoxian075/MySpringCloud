package com.scd.coredb.feign;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.PayRecordService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.PayRecordSelectParam;
import com.scd.joggle.pojo.po.PayRecordPo;
import com.scd.joggle.pojo.po.PayResultPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payRecord")
public class FPayRecord {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PayRecordService payRecordService;
    
    @RequestMapping(value = "/list")
    public Return<PageInfo<PayRecordPo>> list(@RequestBody PayRecordSelectParam param) {
        Return<PageInfo<PayRecordPo>> ret = null;
        try {
            ret = payRecordService.list(param);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return ret;
    }
    
    @RequestMapping(value = "/getList")
	Return<List<PayResultPo>> getList(int page, int size, String account) {
        Return<List<PayResultPo>> ret = null;
        try {
            ret = payRecordService.getList(page, size, account);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return ret;
    }



}


