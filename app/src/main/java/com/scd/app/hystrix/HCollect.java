package com.scd.app.hystrix;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FCollect;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CollectPo;
import com.scd.joggle.pojo.po.CommodityPo;


import com.scd.sdk.util.pojo.Return;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2018-03-14.
 */
@Component
public class HCollect implements FCollect {
    @Override
    public boolean addCollect(@RequestBody CommodityPo commodityPo,
                              @RequestParam(value = "accountId") long accountId,
                              @RequestParam(value = "account") String account) {
        return false;
    }

    @Override
    public Return<CollectPo> findOne(@RequestParam(value = "commodityId") long commodityId,
                                    @RequestParam(value = "account") String account) {
        return Constant.createReturn(ErrorCom.FEIGN_ERROR);
    }

    @Override
    public boolean cancelCollect(@RequestParam(value = "commodityId") long commodityId,
                                 @RequestParam(value = "account") String account) {
        return false;
    }
}
