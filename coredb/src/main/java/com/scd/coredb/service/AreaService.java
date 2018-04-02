package com.scd.coredb.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.AreaDao;
import com.scd.coredb.pojo.db.TArea;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.sdk.util.pojo.Return;

@Service
public class AreaService {
	
	@Autowired
	private AreaDao areaDao;
	
	public Return<List<AreaShortPojo>> findByParentId(long parentId) {
		List<TArea> areaList = areaDao.findByParentId(parentId);
		if (areaList == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		
		List<AreaShortPojo> areaShortList = new ArrayList<AreaShortPojo>();
    	for (TArea tArea : areaList) {
    		areaShortList.add(tArea.createShort());
    	}
    	
    	return Constant.createReturn(areaShortList);
	}
	
	public TArea findOne(long id) {
		return areaDao.findOne(id);
	}

	public List<TArea> findAllArea() {
		Specification<TArea> spec = new Specification<TArea>() {
			@Override
			public Predicate toPredicate(Root<TArea> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(criteriaBuilder.equal(root.get(TArea.LEVEl).as(Integer.class), 1));	// 省
				predicates.add(criteriaBuilder.equal(root.get(TArea.LEVEl).as(Integer.class), 2));	// 市
				predicates.add(criteriaBuilder.equal(root.get(TArea.LEVEl).as(Integer.class), 3));	// 区/县
	            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		Order order = new Order(Direction.ASC, TArea.LEVEl);
		List<TArea> listArea = areaDao.findAll(spec, new Sort(order));
		return listArea;
	}	
}
