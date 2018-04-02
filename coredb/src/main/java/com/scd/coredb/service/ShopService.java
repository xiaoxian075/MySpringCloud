package com.scd.coredb.service;

import com.google.common.collect.Lists;
import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.CommodityDao;
import com.scd.coredb.dao.ShopDao;
import com.scd.coredb.pojo.db.TCommodity;
import com.scd.coredb.pojo.db.TShop;
import com.scd.coredb.pojo.util.ShopUtil;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.ShopPo;
import com.scd.sdk.util.GsonUtil;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {
	
	@Autowired
	private ShopDao shopDao;

	@Autowired
    private CommodityDao commodityDao;

	public Return<List<ShopPo>> getAllShop() {
		Iterable<TShop> iter = shopDao.findAll();
		List<TShop> shopList = Lists.newArrayList(iter);

		List<ShopPo> poList = ShopUtil.change(shopList);
		if (poList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(poList);
	}

	public Return<PageInfo<ShopPo>> list(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<TShop> pageInfo = shopDao.findAll(pageable);

        page = pageInfo.getNumber() + 1;
        long total = pageInfo.getTotalElements();
        List<TShop> kdList = pageInfo.getContent();
        List<ShopPo> voList = ShopUtil.change(kdList);

        PageInfo<ShopPo> kdPage = new PageInfo<ShopPo>(page, total, voList);

        return Constant.createReturn(kdPage);
	}

	public Return<Object> add(String name, List<String> picList) {
        TShop tShop = shopDao.findByName(name);
        if (tShop != null) {
            return Constant.createReturn(ErrorCom.SHOP_NAME_EXIST);
        }
        
        String listPic = GsonUtil.toString(picList);
        if (listPic == null) {
        	return Constant.createReturn(ErrorCom.CHANGE_ERROR);
        }

        long curTime = System.currentTimeMillis();
        tShop = new TShop(0, name, listPic, curTime, curTime);
        shopDao.save(tShop);
        
        return Constant.createReturn();
	}

	public Return<Object> edit(long id, String name, List<String> picList) {

        TShop tShop = shopDao.findOne(id);
        if (tShop == null) {
            return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
        }
        
        if (picList == null || picList.size() == 0) {
	        String listPic = GsonUtil.toString(picList);
	        if (listPic == null) {
	        	return Constant.createReturn(ErrorCom.CHANGE_ERROR);
	        }
        }

        boolean isUpdate = false;
        if (!name.equals(tShop.getName())) {
            TShop tmpNode = shopDao.findByName(name);
            if (tmpNode != null) {
                return Constant.createReturn(ErrorCom.SHOP_NAME_EXIST);
            }
            tShop.setName(name);
            isUpdate = true;
        }
        
        if (picList != null) {
        	String strListPic = GsonUtil.toString(picList);
        	if (strListPic != null) {
	        	if (!strListPic.equals(tShop.getListPic())) {
		        	tShop.setListPic(strListPic);
		        	isUpdate = true;
	        	}
        	}
        }

        if (!isUpdate) {
            return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
        }

        tShop.setUpdateTime(System.currentTimeMillis());
        shopDao.save(tShop);

        return Constant.createReturn();
	}
	
	public Return<Long> del(long id) {
        Specification<TCommodity> spec = new Specification<TCommodity>() {
            @Override
            public Predicate toPredicate(Root<TCommodity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get(TCommodity.SHOP_ID).as(Long.class), id));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        long count = commodityDao.count(spec);
        if (count > 0) {
            return Constant.createReturn(ErrorCom.SHOP_IS_USE);
        }
		TShop tShop = shopDao.findOne(id);
		if (tShop == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		shopDao.delete(id);
    	
    	return Constant.createReturn(tShop.getId());
	}
	
	private boolean _del(long id) {
		Specification<TCommodity> spec = new Specification<TCommodity>() {
            @Override
            public Predicate toPredicate(Root<TCommodity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get(TCommodity.SHOP_ID).as(Long.class), id));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        long count = commodityDao.count(spec);
        if (count > 0) {
            return false;
        }
		TShop tShop = shopDao.findOne(id);
		if (tShop == null) {
			return false;
		}
		shopDao.delete(id);
		
		return true;
	}

	public Return<List<Long>> batchDel(List<Long> idList) {
        for (long id : idList) {
            if (!_del(id)) {
            	return Constant.createReturn(ErrorCom.DELETE_FAIL);
            }
        }
        return Constant.createReturn(idList);
	}

	public Return<ShopPo> getShop(long shopId) {
		TShop tShop = shopDao.findOne(shopId);
		if (tShop == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		ShopPo po = ShopUtil.change(tShop);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(po);
	}

}
