package com.scd.app.feign;

import com.scd.app.hystrix.HCollect;
import com.scd.joggle.pojo.po.CollectPo;
import com.scd.joggle.pojo.po.CommodityPo;


import com.scd.sdk.util.pojo.Return;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2018-03-14.
 */
@FeignClient(value = "${feign.coredb}", fallback = HCollect.class)
public interface FCollect {

    @RequestMapping(value = "/collect/addCollect")
    boolean addCollect(@RequestBody CommodityPo commodityPo,
                       @RequestParam(value = "accountId") long accountId,
                       @RequestParam(value = "account") String account);


    @RequestMapping(value = "/collect/findOne")
    Return<CollectPo> findOne(@RequestParam(value = "commodityId") long commodityId,
                             @RequestParam(value = "account") String account);

    @RequestMapping(value = "/collect/cancelCollect")
    boolean cancelCollect(@RequestParam(value = "commodityId") long commodityId,
                          @RequestParam(value = "account") String account);
}
