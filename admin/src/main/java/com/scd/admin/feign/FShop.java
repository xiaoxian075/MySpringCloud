package com.scd.admin.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HShop;
import com.scd.joggle.pojo.po.ShopPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HShop.class)
public interface FShop {

    @RequestMapping(value = "/shop/getAllShop", method = RequestMethod.POST)
    Return<List<ShopPo>> getAllShop();
    
    @RequestMapping(value = "/shop/list", method = RequestMethod.POST)
    Return<PageInfo<ShopPo>> list(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size);
    
    @RequestMapping(value = "/shop/del")
    Return<Long> del(@RequestParam(value = "id") long id);

    @RequestMapping(value = "/shop/batchDel")
    Return<List<Long>> batchDel(@RequestBody List<Long> idList);

    @RequestMapping(value = "/shop/add")
    Return<Object> add(@RequestParam(value = "name") String name, @RequestBody List<String> listPic);

    @RequestMapping(value = "/shop/edit")
    Return<Object> edit(@RequestParam(value = "id") long id, @RequestParam(value = "name") String name, @RequestBody List<String> listPic);
}
