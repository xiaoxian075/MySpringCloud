package com.scd.coredb.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.scd.joggle.constant.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.AreaDao;
import com.scd.coredb.dao.CommodityAttrDao;
import com.scd.coredb.dao.CommodityDao;
import com.scd.coredb.dao.ShopDao;
import com.scd.coredb.pojo.db.TArea;
import com.scd.coredb.pojo.db.TCommodity;
import com.scd.coredb.pojo.db.TCommodityAttr;
import com.scd.coredb.pojo.db.TShop;
import com.scd.coredb.pojo.util.CommodityUtil;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.CommodityAttrParam;
import com.scd.joggle.pojo.param.CommodityParam;
import com.scd.joggle.pojo.param.CommoditySelectParam;
import com.scd.joggle.pojo.po.CommodityAttrPo;
import com.scd.joggle.pojo.po.CommodityOriPo;
import com.scd.joggle.pojo.po.CommodityPo;
import com.scd.sdk.util.GsonUtil;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class CommodityService {
	
	@Autowired
	private CommodityDao commodityDao;
	
	@Autowired
	private CommodityAttrDao commodityAttrDao;
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private AreaDao areaDao;
	
	

	public Return<List<CommodityPo>> getPageIncludeAttr(long shopId, int type, int page, int size) {
		Specification<TCommodity> spec = new Specification<TCommodity>() {
    		@Override
    		public Predicate toPredicate(Root<TCommodity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TCommodity.STATE).as(Integer.class), Type.COMMODITY_STATE_UP));
    			if (shopId > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TCommodity.SHOP_ID).as(Long.class), shopId));
    			}
    			
    			if (type > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TCommodity.SHOP_PRODUCT_TYPE).as(Integer.class), type));
    			}
    
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		Order order = new Order(Direction.DESC, TCommodity.ALL_SALE_NUM);
		Pageable pageable = new PageRequest(page, size, new Sort(order));
		Page<TCommodity> pageInfo = commodityDao.findAll(spec, pageable);
		if (pageInfo == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		List<TCommodity> commodityList = pageInfo.getContent();
		if (commodityList == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		List<CommodityPo> listPo = new ArrayList<CommodityPo>();
    	for (TCommodity tCommodity : commodityList) {
    		List<TCommodityAttr> commodityAttrlist = commodityAttrDao.findByCommodityId(tCommodity.getId());
    		if (commodityAttrlist == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}
    		
    		TShop tShop = shopDao.findOne(tCommodity.getShopId());
    		if (tShop == null) {
    			return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
    		}
    		
    		CommodityPo po = CommodityUtil.changePo(tCommodity);
    		if (po == null) {
    			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
    		}
    		po.setShopName(tShop.getName());
    		
    		List<CommodityAttrPo> attrPoList = new ArrayList<CommodityAttrPo>();
    		for (TCommodityAttr tCommodityAttr : commodityAttrlist) {
    			CommodityAttrPo attrPo = tCommodityAttr.createPo();
    			if (attrPo == null) {
	    			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
	    		}
    			attrPoList.add(attrPo);
    		}
    		
    		po.setAttrList(attrPoList);
    		
    		listPo.add(po);
    	}
    	
		return Constant.createReturn(listPo);
	}

	public Return<CommodityPo> getOne(long id) {
		TCommodity tCommodity = commodityDao.findOne(id);
		if (tCommodity == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		TShop tShop = shopDao.findOne(tCommodity.getShopId());
		if (tShop == null) {
			return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
		}
		
		CommodityPo po = CommodityUtil.changePo(tCommodity);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		po.setShopName(tShop.getName());
		
		List<TCommodityAttr> commodityAttrlist = commodityAttrDao.findByCommodityId(tCommodity.getId());
		if (commodityAttrlist == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		
		List<CommodityAttrPo> attrPoList = new ArrayList<CommodityAttrPo>();
		for (TCommodityAttr tCommodityAttr : commodityAttrlist) {
			CommodityAttrPo attrPo = tCommodityAttr.createPo();
			if (attrPo == null) {
    			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
    		}
			attrPoList.add(attrPo);
		}
		
		po.setAttrList(attrPoList);
		return Constant.createReturn(po);
	}

	public Return<PageInfo<CommodityOriPo>> getPage(CommoditySelectParam commodityParam) {
		Specification<TCommodity> spec = new Specification<TCommodity>() {
    		@Override
    		public Predicate toPredicate(Root<TCommodity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			if (commodityParam.getState() >= 0) {
        			predicates.add(criteriaBuilder.equal(root.get(TCommodity.STATE).as(Integer.class), commodityParam.getState()));
    			}
    			if (commodityParam.getShopId() > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TCommodity.SHOP_ID).as(Long.class), commodityParam.getShopId()));
    			}
    			if (commodityParam.getShopProductType() > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TCommodity.SHOP_PRODUCT_TYPE).as(Integer.class), commodityParam.getShopProductType()));
    			}
    
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		int page = commodityParam.getPage();
		int size = commodityParam.getSize();
		Order order = new Order(Direction.DESC, TCommodity.ID);
		Pageable pageable = new PageRequest(page, size, new Sort(order));
		Page<TCommodity> pageInfo = commodityDao.findAll(spec, pageable);
		if (pageInfo == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		List<TCommodity> commodityList = pageInfo.getContent();
		if (commodityList == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		List<CommodityOriPo> listPo = new ArrayList<CommodityOriPo>();
    	for (TCommodity tCommodity : commodityList) {
    		TShop tShop = shopDao.findOne(tCommodity.getShopId());
    		if (tShop == null) {
    			return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
    		}
    		
    		CommodityOriPo po = CommodityUtil.changeOriPo(tCommodity);
    		if (po == null) {
    			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
    		}
    		po.setShopName(tShop.getName());
    		
    		listPo.add(po);
    	}
    	
		page = pageInfo.getNumber() + 1;
		long total = pageInfo.getTotalElements();
		
		PageInfo<CommodityOriPo> returnPage = new PageInfo<CommodityOriPo>(page, total, listPo);
    	
		return Constant.createReturn(returnPage);
	}

	public Return<CommodityOriPo> add(CommodityParam commodityParam) {
		TArea tArea = areaDao.findOne(commodityParam.getAddrId());
		if (tArea == null) {
			return Constant.createReturn(ErrorCom.AREA_NOT_EXIST);
		}
		
		TShop tShop = shopDao.findOne(commodityParam.getShopId());
		if (tShop == null) {
			return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
		}
		
		TCommodity tCommodity = CommodityUtil.change(commodityParam);
		if (tCommodity == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		tCommodity.setAddrName(tArea.getName());
		
		TCommodity newTCommodity = commodityDao.save(tCommodity);
		if (newTCommodity == null) {
			return Constant.createReturn(ErrorCom.SAVE_FAIL);
		}
		
		CommodityOriPo po = CommodityUtil.changeOriPo(newTCommodity);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		po.setShopName(tShop.getName());
		
		return Constant.createReturn(po);
	}

	public Return<CommodityOriPo> edit(CommodityParam commodityParam) {
		TCommodity tCommodity = commodityDao.findOne(commodityParam.getId());
		if (tCommodity == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		String strListPic = GsonUtil.toString(commodityParam.getListPic());
		if (strListPic == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}

		boolean isUpdate = false;
		if (!tCommodity.getShortTitle().equals(commodityParam.getShortTitle())) {
			tCommodity.setShortTitle(commodityParam.getShortTitle());
			isUpdate = true;
		}
		if (!tCommodity.getFullTitle().equals(commodityParam.getFullTitle())) {
			tCommodity.setFullTitle(commodityParam.getFullTitle());
			isUpdate = true;
		}
		if (tCommodity.getShopId() != commodityParam.getShopId()) {
			TShop tShop = shopDao.findOne(commodityParam.getShopId());
			if (tShop == null) {
				return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
			}
			tCommodity.setShopId(tShop.getId());
			isUpdate = true;
		}
		if (tCommodity.getShopProductType() != commodityParam.getShopProductType()) {
			tCommodity.setShopProductType(commodityParam.getShopProductType());
			isUpdate = true;
		}
		if (!tCommodity.getShowPic().equals(commodityParam.getShowPic())) {
			tCommodity.setShowPic(commodityParam.getShowPic());
			isUpdate = true;
		}
		if (!tCommodity.getListPic().equals(strListPic)) {
			tCommodity.setListPic(strListPic);
			isUpdate = true;
		}
		if (tCommodity.getAddrId() >0 && tCommodity.getAddrId() != commodityParam.getAddrId()) {
			TArea tArea = areaDao.findOne(commodityParam.getAddrId());
			if (tArea == null) {
				return Constant.createReturn(ErrorCom.AREA_NOT_EXIST);
			}
			tCommodity.setAddrId(tArea.getId());
			tCommodity.setAddrName(tArea.getName());
			isUpdate = true;
		}
		if (commodityParam.getServiceItem() != null && !tCommodity.getServiceItem().equals(commodityParam.getServiceItem())) {
			tCommodity.setServiceItem(commodityParam.getServiceItem());
			isUpdate = true;
		}
		if (tCommodity.getIsFreeExp() != commodityParam.getIsFreeExp()) {
			tCommodity.setIsFreeExp(commodityParam.getIsFreeExp());
			isUpdate = true;
		}
		
		if (!isUpdate) {
			return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
		}
		
		tCommodity.setUpdateTime(System.currentTimeMillis());
		
		TCommodity newTCommodity = commodityDao.save(tCommodity);
		if (newTCommodity == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		
		CommodityOriPo po = CommodityUtil.changeOriPo(newTCommodity);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		TShop tShop = shopDao.findOne(po.getShopId());
		if (tShop == null) {
			return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
		}
		po.setShopName(tShop.getName());
		
		return Constant.createReturn(po);
	}

	public Return<Long> del(long id) {
		TCommodity tCommodity = commodityDao.findOne(id);
		if (tCommodity == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		commodityDao.delete(id);
    	
    	return Constant.createReturn(tCommodity.getId());
	}

	@Transactional
	public Return<List<Long>> batchDel(List<Long> idList) {
		for (long id : idList) {
			commodityDao.delete(id);
		}
    	
    	return Constant.createReturn(idList);
	}

	public Return<CommodityOriPo> setState(long id, int state) {
		TCommodity tCommodity = commodityDao.findOne(id);
		if (tCommodity == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		if (tCommodity.getState() == state) {
			return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
		}
		
		if (state == Type.COMMODITY_STATE_UP) {
			// 如果是上架，要检查商品有添加属性
			Specification<TCommodityAttr> spec = new Specification<TCommodityAttr>() {
	    		@Override
	    		public Predicate toPredicate(Root<TCommodityAttr> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		
	    			List<Predicate> predicates = new ArrayList<Predicate>();
	    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.COMMODITY_ID).as(Long.class), id));
	    			
	    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	    		}
			};
			long count = commodityAttrDao.count(spec);
			if (count <= 0) {
				return Constant.createReturn(ErrorCom.COMMODITY_NOT_HAS_ATTR);
			}
		}
		
		tCommodity.setState(state);
		tCommodity.setUpdateTime(System.currentTimeMillis());

		TCommodity newTCommodity = commodityDao.save(tCommodity);
		if (newTCommodity == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		
		CommodityOriPo po = CommodityUtil.changeOriPo(newTCommodity);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		TShop tShop = shopDao.findOne(po.getShopId());
		if (tShop == null) {
			return Constant.createReturn(ErrorCom.SHOP_NOT_EXIST);
		}
		po.setShopName(tShop.getName());
		
		return Constant.createReturn(po);
	}

	public Return<PageInfo<CommodityAttrPo>> getAttrPage(long commodityId, int page, int size) {
		Specification<TCommodityAttr> spec = new Specification<TCommodityAttr>() {
    		@Override
    		public Predicate toPredicate(Root<TCommodityAttr> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.COMMODITY_ID).as(Long.class), commodityId));
    			
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		Order order = new Order(Direction.DESC, TCommodityAttr.ATTR_ID);
		Pageable pageable = new PageRequest(page, size, new Sort(order));
		Page<TCommodityAttr> pageInfo = commodityAttrDao.findAll(spec, pageable);
		if (pageInfo == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		List<TCommodityAttr> commodityList = pageInfo.getContent();
		if (commodityList == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		List<CommodityAttrPo> poList = CommodityUtil.change(commodityList);
    	
		page = pageInfo.getNumber() + 1;
		long total = pageInfo.getTotalElements();
		
		PageInfo<CommodityAttrPo> returnPage = new PageInfo<CommodityAttrPo>(page, total, poList);
    	
		return Constant.createReturn(returnPage);
	}

	public Return<CommodityAttrPo> attrAdd(CommodityAttrParam param) {
		long curTime = System.currentTimeMillis();
		
		TCommodity tCommodity = commodityDao.findOne(param.getCommodityId());
		if (tCommodity == null) {
			return Constant.createReturn(ErrorCom.COMMODITY_NOT_EXIST);
		}
		
		Specification<TCommodityAttr> spec = new Specification<TCommodityAttr>() {
    		@Override
    		public Predicate toPredicate(Root<TCommodityAttr> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.COMMODITY_ID).as(Long.class), param.getCommodityId()));
    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.ATTR_ID).as(Long.class), param.getAttrId()));
    			
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		
		long count = commodityAttrDao.count(spec);
		if (count > 0) {
			return Constant.createReturn(ErrorCom.COMMODITY_ATTR_HAS_EXIST);
		}
		
		TCommodityAttr tCommodityAttr = new TCommodityAttr(
				0,
				param.getCommodityId(),
				param.getAttrId(),
				param.getAttrName(),
				param.getPrice(),
				param.getStockNum(),
				curTime,
				curTime
				);
		
		TCommodityAttr newTCommodityAttr = commodityAttrDao.save(tCommodityAttr);
		if (newTCommodityAttr == null) {
			return Constant.createReturn(ErrorCom.SAVE_FAIL);
		}
		
		CommodityAttrPo po = CommodityUtil.changeAttr(newTCommodityAttr);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(po);
	}

	public Return<CommodityAttrPo> attrEdit(CommodityAttrParam param) {
		
		TCommodityAttr tCommodityAttr = commodityAttrDao.findOne(param.getId());
		if (tCommodityAttr == null) {
			return Constant.createReturn(ErrorCom.COMMODITY_ATTR_NOT_EXIST);
		}
		
		TCommodity tCommodity = commodityDao.findOne(param.getCommodityId());
		if (tCommodity == null) {
			return Constant.createReturn(ErrorCom.COMMODITY_NOT_EXIST);
		}
		
		boolean isUpdate = false;
		
		if (param.getAttrId() != tCommodityAttr.getAttrId()) {
			Specification<TCommodityAttr> spec = new Specification<TCommodityAttr>() {
	    		@Override
	    		public Predicate toPredicate(Root<TCommodityAttr> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		
	    			List<Predicate> predicates = new ArrayList<Predicate>();
	    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.COMMODITY_ID).as(Long.class), param.getCommodityId()));
	    			predicates.add(criteriaBuilder.equal(root.get(TCommodityAttr.ATTR_ID).as(Long.class), param.getAttrId()));
	    			
	    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	    		}
			};
			
			long count = commodityAttrDao.count(spec);
			if (count > 0) {
				return Constant.createReturn(ErrorCom.COMMODITY_ATTR_HAS_EXIST);
			}
			
			tCommodityAttr.setAttrId(param.getAttrId());
			isUpdate = true;
		}
		
		if (!param.getAttrName().equals(tCommodityAttr.getAttrName())) {
			tCommodityAttr.setAttrName(param.getAttrName());
			isUpdate = true;
		}
		if (param.getPrice().compareTo(tCommodityAttr.getPrice()) != 0) {
			tCommodityAttr.setPrice(param.getPrice());
			isUpdate = true;
		}
		if (param.getStockNum() != tCommodityAttr.getStockNum()) {
			tCommodityAttr.setStockNum(param.getStockNum());
			isUpdate = true;
		}
		
		if (!isUpdate) {
			return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
		}
		tCommodityAttr.setUpdateTime(System.currentTimeMillis());
		
		TCommodityAttr newTCommodityAttr = commodityAttrDao.save(tCommodityAttr);
		if (newTCommodityAttr == null) {
			return Constant.createReturn(ErrorCom.SAVE_FAIL);
		}
		
		CommodityAttrPo po = CommodityUtil.changeAttr(newTCommodityAttr);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(po);
	}

	public Return<Long> attrDel(long id) {
		TCommodityAttr tCommodityAttr = commodityAttrDao.findOne(id);
		if (tCommodityAttr == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		commodityAttrDao.delete(id);
    	
    	return Constant.createReturn(tCommodityAttr.getId());
	}

	public Return<List<Long>> attrBatchDel(List<Long> idList) {
		for (long id : idList) {
			commodityAttrDao.delete(id);
		}
    	
    	return Constant.createReturn(idList);
	}

	public Return<List<CommodityPo>> getAssort(long id) {
		Pageable pageable = new PageRequest(0, 3);
		Page<TCommodity> pageInfo = commodityDao.findAll(pageable);
		List<TCommodity> tList = pageInfo.getContent();
		
		List<CommodityPo> poList = new ArrayList<CommodityPo>();
		for (TCommodity tCommodity : tList) {
			CommodityPo po = _getOne(tCommodity);
			if (po != null) {
				poList.add(po);
			}
		}
		return Constant.createReturn(poList);
	}

	public Return<List<CommodityPo>> getRecommend(long id) {
		Pageable pageable = new PageRequest(0, 2);
		Page<TCommodity> pageInfo = commodityDao.findAll(pageable);
		List<TCommodity> tList = pageInfo.getContent();
		
		List<CommodityPo> poList = new ArrayList<CommodityPo>();
		for (TCommodity tCommodity : tList) {
			CommodityPo po = _getOne(tCommodity);
			if (po != null) {
				poList.add(po);
			}
		}
		return Constant.createReturn(poList);
	}
	
	private CommodityPo _getOne(TCommodity tCommodity) {
		if (tCommodity == null) {
			return null;
		}
		TShop tShop = shopDao.findOne(tCommodity.getShopId());
		if (tShop == null) {
			return null;
		}
		
		CommodityPo po = CommodityUtil.changePo(tCommodity);
		if (po == null) {
			return null;
		}
		po.setShopName(tShop.getName());
		
		List<TCommodityAttr> commodityAttrlist = commodityAttrDao.findByCommodityId(tCommodity.getId());
		if (commodityAttrlist == null) {
			return null;
		}
		
		
		List<CommodityAttrPo> attrPoList = new ArrayList<CommodityAttrPo>();
		for (TCommodityAttr tCommodityAttr : commodityAttrlist) {
			CommodityAttrPo attrPo = tCommodityAttr.createPo();
			if (attrPo == null) {
    			return null;
    		}
			attrPoList.add(attrPo);
		}
		
		po.setAttrList(attrPoList);
		return po;
	}
}
