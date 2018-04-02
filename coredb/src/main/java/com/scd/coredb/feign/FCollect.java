package com.scd.coredb.feign;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.pojo.db.TCollect;
import com.scd.coredb.service.CollectService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.po.CollectPo;
import com.scd.joggle.pojo.po.CommodityPo;
import com.scd.sdk.util.pojo.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018-03-13.
 */
@RestController
@RequestMapping(value = "/collect")
public class FCollect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CollectService collectService;

    @RequestMapping(value = "/addCollect")
    public boolean insertOne(@RequestBody CommodityPo commodityPo,long accountId,String account) {
        boolean bReturn = false;
        try {
            TCollect tCollect = new TCollect(
                    commodityPo.getId(),
                    accountId,
                    account,
                    commodityPo.getId(),
                    System.currentTimeMillis(),
                    Type.COLLECT_STATE_DOWN,
                    commodityPo.getShortTitle(),
                    commodityPo.getShowPic(),
                    commodityPo.getAttrList().get(0).getPrice()
            );
            bReturn = collectService.insertOne(tCollect);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return bReturn;
    }

    @RequestMapping(value = "/findOne")
    public Return<CollectPo> findOne(long commodityId, String account) {
        TCollect tCollect = collectService.findByAccountAndId(commodityId,account);
        if (tCollect == null) {
            return Constant.createReturn(ErrorCom.NOT_COLLECT);
        }
        CollectPo collectPo = new CollectPo(
                tCollect.getId(),
                tCollect.getAccountId(),
                tCollect.getAccount(),
                tCollect.getCommodityId(),
                tCollect.getCommodityName(),
                tCollect.getCreateTime(),
                tCollect.getCommodityImg(),
                tCollect.getPrice()
        );
        return Constant.createReturn(collectPo);
    }


    @RequestMapping(value = "/cancelCollect")
    public boolean cancelCollect(long commodityId,String account) {
        boolean bReturn = false;
        try {
            bReturn = collectService.delectCollect(commodityId,account);
        } catch (Exception e) {
            logger.error( e.getMessage());
            return false;
        }
        return bReturn;
    }
}
