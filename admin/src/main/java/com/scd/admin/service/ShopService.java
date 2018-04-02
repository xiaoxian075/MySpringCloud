package com.scd.admin.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FShop;
import com.scd.admin.pojo.util.ShopUtil;
import com.scd.admin.pojo.vo.ShopVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class ShopService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FShop fShop;

	public Return<List<ShopVo>> getAllShop() {
		Return<List<ShopPo>> ret = fShop.getAllShop();
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		List<ShopPo> poList = ret.getData();
		List<ShopVo> voList = ShopUtil.change(poList);
		if (voList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(voList);
	}
	
    public Return<PageInfo<ShopVo>> list(int page, int size) {
        PageInfo<ShopVo> kdPage = null;
        try {
            Return<PageInfo<ShopPo>> ret = fShop.list(page, size);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }

            PageInfo<ShopPo> pageInfo = ret.getData();
            List<ShopPo> poList = pageInfo.getList();
            List<ShopVo> voList = ShopUtil.change(poList);

            kdPage = new PageInfo<ShopVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }

        return Constant.createReturn(kdPage);
    }


    public Return<Long> del(long id) {
        long returnId = 0;
        try {
            Return<Long> ret = fShop.del(id);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }
            returnId = ret.getData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn(returnId);
    }

    @Transactional
    public Return<List<Long>> batchDel(List<Long> idList) {
        List<Long> returnIdList = null;
        try {
            Return<List<Long>> ret = fShop.batchDel(idList);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }
            returnIdList = ret.getData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn(returnIdList);
    }


    public Return<Object> add(String name, List<String> listPic) {
        try {
            Return<Object> ret = fShop.add(name, listPic);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn();
    }


    public Return<Object> edit(long id, String name, List<String> listPic) {
        try {
            Return<Object> ret = fShop.edit(id, name, listPic);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn();
    }

}
