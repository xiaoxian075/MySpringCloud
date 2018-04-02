package com.scd.coredb.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.ShopActivityDao;
import com.scd.coredb.dao.ShopDao;
import com.scd.coredb.pojo.db.TShop;
import com.scd.coredb.pojo.db.TShopActivity;
import com.scd.coredb.pojo.util.ShopActivityUtil;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopActivityPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class ShopActivityService {
	
	@Autowired
	private ShopActivityDao shopActivityDao;
	
	@Autowired
	private ShopDao shopDao;

	public Return<List<ShopActivityPo>> getList(long shopId) {
		List<TShopActivity> tList = shopActivityDao.findByShopId(shopId);
		if (tList == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		
		List<ShopActivityPo> poList = ShopActivityUtil.change(tList);
		if (poList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(poList);
	}

	public Return<PageInfo<ShopActivityPo>> list(int page, int size, long shopId) {
		Specification<TShopActivity> spec = new Specification<TShopActivity>() {
    		@Override
    		public Predicate toPredicate(Root<TShopActivity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			if (shopId > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TShopActivity.SHOP_ID).as(Long.class), shopId));
    			}
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		
		Pageable pageable = new PageRequest(page, size);
        Page<TShopActivity> pageInfo = shopActivityDao.findAll(spec, pageable);

        page = pageInfo.getNumber() + 1;
        long total = pageInfo.getTotalElements();
        List<TShopActivity> kdList = pageInfo.getContent();
        List<ShopActivityPo> voList = ShopActivityUtil.change(kdList);

        PageInfo<ShopActivityPo> kdPage = new PageInfo<ShopActivityPo>(page, total, voList);

        return Constant.createReturn(kdPage);
	}

	public Return<ShopActivityPo> add(long shopId, String title) {
		TShop tShop = shopDao.findOne(shopId);
        if (tShop == null) {
            return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
        }

        long curTime = System.currentTimeMillis();
        TShopActivity tShopActivity = new TShopActivity(0, shopId, title, curTime, curTime);
        TShopActivity newTShopActivity = shopActivityDao.save(tShopActivity);
        if (newTShopActivity == null) {
        	return Constant.createReturn(ErrorCom.SAVE_ERROR);
        }
        
        ShopActivityPo po = ShopActivityUtil.change(newTShopActivity);
        if (po == null) {
        	return Constant.createReturn(ErrorCom.CHANGE_ERROR);
        }
        
        return Constant.createReturn(po);
	}

	public Return<ShopActivityPo> edit(long id, long shopId, String title) {
		TShopActivity tShopActivity = shopActivityDao.findOne(id);
        if (tShopActivity == null) {
            return Constant.createReturn(ErrorCom.NOT_EXIST);
        }
        
        boolean isUpdate = false;
        if (shopId > 0 && tShopActivity.getShopId() != shopId) {
        	TShop tShop = shopDao.findOne(shopId);
            if (tShop == null) {
                return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
            }
            tShopActivity.setShopId(shopId);
            isUpdate = true;
        }
        
        if (title != null && !title.equals(tShopActivity.getTitle())) {
        	 tShopActivity.setTitle(title);
             isUpdate = true;
        }
        
        if (!isUpdate) {
        	return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
        }

        tShopActivity.setUpdateTime(System.currentTimeMillis());
        TShopActivity newTShopActivity = shopActivityDao.save(tShopActivity);
        if (newTShopActivity == null) {
        	return Constant.createReturn(ErrorCom.SAVE_ERROR);
        }
        
        ShopActivityPo po = ShopActivityUtil.change(newTShopActivity);
        if (po == null) {
        	return Constant.createReturn(ErrorCom.CHANGE_ERROR);
        }
        
        return Constant.createReturn(po);
	}

	public Return<Long> del(long id) {
		TShopActivity tShopActivity = shopActivityDao.findOne(id);
		if (tShopActivity == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		shopActivityDao.delete(id);
    	
    	return Constant.createReturn(tShopActivity.getId());
	}

	public Return<List<Long>> batchDel(List<Long> idList) {
		List<Long> returnList = new ArrayList<Long>();
        for (long id : idList) {
        	Return<Long> ret = del(id);
        	if (!Return.isErr(ret)) {
        		returnList.add(ret.getData());
        	}
        }
        return Constant.createReturn(returnList);
	}
}
