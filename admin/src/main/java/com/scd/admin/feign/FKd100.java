package com.scd.admin.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HKd100;
import com.scd.joggle.pojo.po.Kd100Po;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HKd100.class)
public interface FKd100 {

    @RequestMapping(value = "/kd100/list", method = RequestMethod.POST)
    Return<PageInfo<Kd100Po>> list(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size);

    @RequestMapping(value = "/kd100/del")
    Return<Long> del(@RequestParam(value = "id") long id);

    @RequestMapping(value = "/kd100/batchDel")
    Return<List<Long>> batchDel(@RequestBody List<Long> idList);

    @RequestMapping(value = "/kd100/add")
    Return<Object> add(@RequestParam(value = "name") String name, @RequestParam(value = "code") String code);

    @RequestMapping(value = "/kd100/edit")
    Return<Object> edit(@RequestParam(value = "id")long id, @RequestParam(value = "name") String name, @RequestParam(value = "code") String code);

    @RequestMapping(value = "/kd100/getAll", method = RequestMethod.POST)
	Return<List<Kd100Po>> getAll();

}
