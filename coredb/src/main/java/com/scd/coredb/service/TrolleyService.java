package com.scd.coredb.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.CommodityAttrDao;
import com.scd.coredb.dao.CommodityDao;
import com.scd.coredb.dao.ShopDao;
import com.scd.coredb.dao.TrolleyDao;
import com.scd.coredb.pojo.db.TCommodity;
import com.scd.coredb.pojo.db.TCommodityAttr;
import com.scd.coredb.pojo.db.TShop;
import com.scd.coredb.pojo.db.TTrolley;
import com.scd.coredb.pojo.util.TrolleyUtil;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.TrolleyPo;
import com.scd.sdk.util.pojo.Return;

@Service
public class TrolleyService {
	
	@Autowired
	private TrolleyDao trolleyDao;

	@Autowired
    private CommodityDao commodityDao;
	
	@Autowired
    private CommodityAttrDao commodityAttrDao;
	
	@Autowired
    private ShopDao shopDao;
	
	public boolean batchDelByComodity(String account, List<Long> comodityList) {
		List<TTrolley> tList = trolleyDao.findByAccount(account);
		if (tList == null) {
			return false;
		}
		
		Map<Long, TTrolley> mapData = new HashMap<Long, TTrolley>();
		for (TTrolley tTrolley : tList) {
			TTrolley hasTrolley = mapData.get(tTrolley.getCommudityId());
			if (hasTrolley != null) {
				hasTrolley.setNum(hasTrolley.getNum() + tTrolley.getNum());
			} else {
				mapData.put(tTrolley.getCommudityId(), tTrolley);
			}
		}
		
		//List<Long> idList = new ArrayList<Long>();
		for (Long commodityId : comodityList) {
			TTrolley tTrolley = mapData.get(commodityId);
			if (tTrolley != null) {
				trolleyDao.delete(tTrolley.getId());
			}
		}
		
		return true;
	}

	public Return<List<TrolleyPo>> getList(String account) {
		List<TTrolley> tList = trolleyDao.findByAccount(account);
		if (tList == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		List<TrolleyPo> poList = TrolleyUtil.change(tList);
		if (poList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(poList);
	}

	public Return<TrolleyPo> add(String odd, String account, long commodityId, long attrId, int num) {
		// 验证是否重复提交
		TTrolley oldTTrolley = trolleyDao.findByOdd(odd);
		if (oldTTrolley != null) {
			return Constant.createReturn(ErrorCom.REPEATED_SUBMIT);
		}
		
		long curTime = System.currentTimeMillis();
		
		TTrolley newTTrolley = null;
		TTrolley tTrolley = getTrolley(account, commodityId, attrId);
		if (tTrolley != null) {
			tTrolley.setNum(tTrolley.getNum() + num);
			tTrolley.setUpdateTime(curTime);
			newTTrolley = trolleyDao.save(tTrolley);
			if (newTTrolley == null) {
				return Constant.createReturn(ErrorCom.SAVE_ERROR);
			}
		} else {
			TCommodity tCommodity = commodityDao.findOne(commodityId);
			if (tCommodity == null) {
				return Constant.createReturn(ErrorCom.COMMODITY_NOT_EXIST);
			}
			String title = tCommodity.getShortTitle();
			String showPic = tCommodity.getShowPic();
			
			TCommodityAttr tCommodityAttr = getCommodityAttr(commodityId, attrId);
			if (tCommodityAttr == null) {
				return Constant.createReturn(ErrorCom.COMMODITY_ATTR_NOT_EXIST);
			}
			String attrName = tCommodityAttr.getAttrName();
			BigDecimal price = tCommodityAttr.getPrice();
			
			long shopId = tCommodity.getShopId();
			TShop tShop = shopDao.findOne(shopId);
			if (tShop == null) {
				return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
			}
			String shopName = tShop.getName();
			
			
			tTrolley = new TTrolley(0L, odd, account, commodityId, title, showPic, attrId, attrName, shopId, shopName, price, num, curTime, curTime);
			newTTrolley = trolleyDao.save(tTrolley);
			if (newTTrolley == null) {
				return Constant.createReturn(ErrorCom.SAVE_ERROR);
			}
		}
		
		TrolleyPo po = TrolleyUtil.change(newTTrolley);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(po);
	}
	
	public Return<TrolleyPo> editNum(long id, String account, int num) {
		TTrolley tTrolley = trolleyDao.findOne(id);
		if (tTrolley == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		if (!tTrolley.getAccount().equals(account)) {
			return Constant.createReturn(ErrorCom.NO_POWER);
		}
		
		tTrolley.setNum(num);
		tTrolley.setUpdateTime(System.currentTimeMillis());
		TTrolley newTTrolley = trolleyDao.save(tTrolley);
		if (newTTrolley == null) {
			return Constant.createReturn(ErrorCom.SAVE_ERROR);
		}
		
		TrolleyPo po = TrolleyUtil.change(newTTrolley);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		return Constant.createReturn(po);
	}

	public Return<List<Long>> batchDel(String account, List<Long> idList) {
		List<TTrolley> trolleyList = trolleyDao.findByAccount(account);
		Map<Long, TTrolley> mapData = new HashMap<Long, TTrolley>();
		for (TTrolley tTrolley : trolleyList) {
			mapData.put(tTrolley.getId(), tTrolley);
		}
		
		List<Long> newIdList = new ArrayList<Long>();
		for (long id : idList) {
			TTrolley tTrolley = mapData.get(id);
			if (tTrolley != null) {
				trolleyDao.delete(id);
				newIdList.add(id);
			}
		}
		return Constant.createReturn(newIdList);
	}
	
	private TCommodityAttr getCommodityAttr(long commodityId, long attrId) {
		Specification<TCommodityAttr> spec = new Specification<TCommodityAttr>() {
    		@Override
    		public Predicate toPredicate(Root<TCommodityAttr> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.COMMODITY_ID).as(Long.class), commodityId));
    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.ATTR_ID).as(Long.class), attrId));
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		
		return commodityAttrDao.findOne(spec);
	}
	
	private TTrolley getTrolley(String account, long commodityId, long attrId) {
		Specification<TTrolley> spec = new Specification<TTrolley>() {
    		@Override
    		public Predicate toPredicate(Root<TTrolley> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TTrolley.ACCOUNT).as(String.class), account));
    			predicates.add(criteriaBuilder.equal(root.get(TTrolley.COMMUDITY_ID).as(Long.class), commodityId));
    			predicates.add(criteriaBuilder.equal(root.get(TTrolley.ATTR_ID).as(Long.class), attrId));
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		
		return trolleyDao.findOne(spec);
	}

}
