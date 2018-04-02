package com.scd.admin.feign;

import com.scd.admin.hystrix.HPayRecord;
import com.scd.joggle.pojo.param.PayRecordSelectParam;
import com.scd.joggle.pojo.po.PayRecordPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${feign.coredb}", fallback = HPayRecord.class)
public interface FPayRecord {

    @RequestMapping(value = "/payRecord/list", method = RequestMethod.POST)
    Return<PageInfo<PayRecordPo>> list(@RequestBody PayRecordSelectParam param);

}
