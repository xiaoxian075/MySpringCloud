package com.scd.admin.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.admin.hystrix.HExpenses;
import com.scd.joggle.pojo.param.ExpensesParam;
import com.scd.joggle.pojo.po.ExpensesPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@FeignClient(value = "${feign.coredb}", fallback = HExpenses.class)
public interface FExpenses {

    @RequestMapping(value = "/expenses/list", method = RequestMethod.POST)
    Return<PageInfo<ExpensesPo>> list(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size);

    @RequestMapping(value = "/expenses/del")
    Return<Long> del(@RequestParam(value = "id") long id);

    @RequestMapping(value = "/expenses/batchDel")
    Return<List<Long>> batchDel(@RequestBody List<Long> idList);

    @RequestMapping(value = "/expenses/add")
    Return<Object> add(@RequestBody ExpensesParam param);

    @RequestMapping(value = "/expenses/edit")
    Return<Object> edit(@RequestBody ExpensesParam param);

}
