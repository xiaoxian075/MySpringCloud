package com.scd.admin.service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FAccount;
import com.scd.admin.pojo.util.AccountUtil;
import com.scd.admin.pojo.vo.AccountVo;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.param.AccountSelectParam;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountService {
   
    @Autowired
    private FAccount fAccount;

    public Return<PageInfo<AccountVo>> getPage(AccountSelectParam param) {
        Return<PageInfo<AccountBo>> ret = fAccount.getPage(param);
        if (Return.isErr(ret)) {
            return Constant.createReturn(ret.getCode(), ret.getDesc());
        }

        PageInfo<AccountBo> pageInfo = ret.getData();
        List<AccountBo> poList = pageInfo.getList();
        List<AccountVo> voList = AccountUtil.change(poList);

        PageInfo<AccountVo> kdPage = new PageInfo<AccountVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);

        return Constant.createReturn(kdPage);
    }


    public Return<Object> state(long id, int type) {
        fAccount.setState(id, type);
        return Constant.createReturn();
    }
}
