package com.scd.admin.service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FPayRecord;
import com.scd.admin.pojo.util.PayRecordUtil;
import com.scd.admin.pojo.vo.PayRecordVo;
import com.scd.joggle.pojo.param.PayRecordSelectParam;
import com.scd.joggle.pojo.po.PayRecordPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayRecordService {

    @Autowired
    private FPayRecord fPayRecord;

    public Return<PageInfo<PayRecordVo>> list(PayRecordSelectParam param) {

        Return<PageInfo<PayRecordPo>> ret = fPayRecord.list(param);
        if (Return.isErr(ret)) {
            return Constant.createReturn(ret.getCode(), ret.getDesc());
        }

        PageInfo<PayRecordPo> pageInfo = ret.getData();
        List<PayRecordPo> poList = pageInfo.getList();
        List<PayRecordVo> voList = PayRecordUtil.change(poList);

        PageInfo<PayRecordVo> kdPage = new PageInfo<PayRecordVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);

        return Constant.createReturn(kdPage);
    }


}
