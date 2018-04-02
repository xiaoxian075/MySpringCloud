package com.scd.coredb.service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.AddressDao;
import com.scd.coredb.dao.AreaDao;
import com.scd.coredb.dao.CommodityDao;
import com.scd.coredb.dao.ExpensesDao;
import com.scd.coredb.pojo.db.TAddress;
import com.scd.coredb.pojo.db.TArea;
import com.scd.coredb.pojo.db.TCommodity;
import com.scd.coredb.pojo.db.TExpenses;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.sdk.util.pojo.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpressService {
	
	@Autowired
	private CommodityDao commodityDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private ExpensesDao expensesDao;
	
	@Autowired
	private AreaDao areaDao;
	
	
	public Return<BigDecimal> getExp(long commodityId, long addressId) {

		TAddress tAddress = addressDao.findOne(addressId);
		if (tAddress == null) {
			return Constant.createReturn(ErrorCom.ADDRESS_NOT_EXIST);
		}
		long addrId = tAddress.getAddrId();
				
		TCommodity tCommodity = commodityDao.findOne(commodityId);
		if (tCommodity == null) {
			return Constant.createReturn(ErrorCom.COMMODITY_NOT_EXIST);
		}
		long commodityAddrId = tCommodity.getAddrId();

		if (tCommodity.getIsFreeExp() == Type.COMMODITY_EXP_ISFREE_YES) {
			return Constant.createReturn(BigDecimal.ZERO);
		}

		BigDecimal expPrice = cacuFreight(addrId, commodityAddrId);
		if (expPrice == null) {
			return Constant.createReturn(ErrorCom.GET_EXP_PRICE_ERROR);
		}
		
		return Constant.createReturn(expPrice);
	}
	
	public Return<List<BigDecimal>> getMulExp(List<List<Long>> commodityIdList, long addressId) {

		TAddress tAddress = addressDao.findOne(addressId);
		if (tAddress == null) {
			return Constant.createReturn(ErrorCom.ADDRESS_NOT_EXIST);
		}
		long addrId = tAddress.getAddrId();
		
		List<BigDecimal> expList = new ArrayList<BigDecimal>();
		
		for (List<Long> idList : commodityIdList) {
			BigDecimal expPrice = BigDecimal.ZERO;
			for (long commodityId : idList) {
				TCommodity tCommodity = commodityDao.findOne(commodityId);
				if (tCommodity == null) {
					continue;
				}
				long commodityAddrId = tCommodity.getAddrId();
		
				if (tCommodity.getIsFreeExp() == Type.COMMODITY_EXP_ISFREE_YES) {
					continue;
				}
		
				BigDecimal _expPrice = cacuFreight(addrId, commodityAddrId);
				if (expPrice == null) {
					continue;
				}
				
				if (expPrice.compareTo(_expPrice) < 0) {
					expPrice = _expPrice;
				}
			}
			
			expList.add(expPrice);
		}
		
		return Constant.createReturn(expList);
	}
	
	private static final BigDecimal defaultExp = new BigDecimal("10.00");
	public BigDecimal cacuFreight(long commodityAddrId, long bullAddrId) {
		long bullProviceAddrId = getProvice(bullAddrId);
		if (bullProviceAddrId <= 0) {
			return defaultExp;
		}
		
		long commodityProviceAddrId = getProvice(commodityAddrId);
		if (commodityProviceAddrId <= 0) {
			return defaultExp;
		}
		
		Specification<TExpenses> spec = new Specification<TExpenses>() {
    		@Override
    		public Predicate toPredicate(Root<TExpenses> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TExpenses.COMMODITY_ADDR_ID).as(Long.class), commodityProviceAddrId));
    			predicates.add(criteriaBuilder.equal(root.get(TExpenses.BULL_ADDR_ID).as(Long.class), bullProviceAddrId));
    			
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		TExpenses tExpenses = expensesDao.findOne(spec);
		if (tExpenses == null) {
			return defaultExp;
		}
		
		return tExpenses.getPrice();
	}

	private long getProvice(long addrId) {
		do {
			TArea tArea = areaDao.findOne(addrId);
			if (tArea == null) {
				return 0;
			}
			if (tArea.getLevel() == 1) {
				return tArea.getId();
			}
			
			addrId = tArea.getParentId();
		} while(true);
	}


}
