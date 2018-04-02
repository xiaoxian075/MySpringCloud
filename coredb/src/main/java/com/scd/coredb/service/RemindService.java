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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.RemindDao;
import com.scd.coredb.pojo.db.TRemind;
import com.scd.coredb.pojo.util.RemindUtil;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.po.RemindPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class RemindService {
	
	@Autowired
	private RemindDao remindDao;

	public Return<PageInfo<RemindPo>> getPage(int page, int size, int state) {
		Specification<TRemind> spec = new Specification<TRemind>() {
    		@Override
    		public Predicate toPredicate(Root<TRemind> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			if (state > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TRemind.STATE).as(Integer.class), state));
    			}
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		Order order = new Order(Direction.DESC, TRemind.CREATE_TIME);
		Pageable pageable = new PageRequest(page, size, new Sort(order));
		Page<TRemind> remindPage = remindDao.findAll(spec, pageable);
		
		List<TRemind> orderList = remindPage.getContent();
		List<RemindPo> poList = RemindUtil.change(orderList);
		if (poList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		page = remindPage.getNumber() + 1;
		long total = remindPage.getTotalElements();
		
		PageInfo<RemindPo> kdPage = new PageInfo<RemindPo>(page, total, poList);
		
		
		return Constant.createReturn(kdPage);
	}

	public Return<Object> ignore(long id) {
		TRemind tRemind = remindDao.findOne(id);
		if (tRemind == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST); 
		}
		
		if (tRemind.getState() != Type.REMIND_NOT_DEAL) {
			return Constant.createReturn(ErrorCom.STATE_FAIL); 
		}
		
		tRemind.setState(Type.REMIND_HAS_DEAL);
		tRemind.setUpdateTime(System.currentTimeMillis());
		TRemind newTRemind = remindDao.save(tRemind);
		if (newTRemind == null) {
			return Constant.createReturn(ErrorCom.SAVE_ERROR);
		}
		
		return Constant.createReturn();
	}
}
