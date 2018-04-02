package com.scd.app.service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FAccount;
import com.scd.app.feign.FCollect;
import com.scd.app.feign.FCommodity;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.po.CollectPo;
import com.scd.joggle.pojo.po.CommodityPo;
import com.scd.joggle.pojo.po.StatePo;
import com.scd.sdk.util.pojo.Return;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018-03-14.
 */
@Service
public class CollectService {

    @Autowired
    private FCollect fCollect;
    @Autowired
    private FAccount fAccount;
    @Autowired
    private FCommodity fCommodity;

    public Return<Object> addCollect(String account, long commodityId) {
        AccountBo accountBo = fAccount.getInfoByAccount(account);
        if(accountBo == null){
            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
        }
        Return<CommodityPo> ret = fCommodity.getOne(commodityId);
        if (Return.isErr(ret)) {
            return Constant.createReturn(ret.getCode(), ret.getDesc());
        }
        Return<CollectPo> one = fCollect.findOne(commodityId, accountBo.getAccount());
        if(one.getData() != null){
            return Constant.createReturn(ErrorCom.ALREADY_COLLECT);
        }
        boolean result = fCollect.addCollect(ret.getData(),accountBo.getId(),accountBo.getAccount());
        if(!result){
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn();
    }

    public Return<Object> cancelCollect(String account, long commodityId) {
        AccountBo accountBo = fAccount.getInfoByAccount(account);
        if(accountBo == null){
            return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
        }
        boolean result = fCollect.cancelCollect(commodityId,accountBo.getAccount());
        if(!result){
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn();
    }


    public Return<StatePo> isCollect(String account, long commodityId) {
        Return<CollectPo> ret = fCollect.findOne(commodityId,account);
        StatePo statePo = new StatePo();
        if(ret.getData() != null){
            statePo.setState(1);//已收藏
        }else{
            statePo.setState(0);//未收藏
        }
        return Constant.createReturn(statePo);
    }
}
