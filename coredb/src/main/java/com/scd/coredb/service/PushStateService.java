package com.scd.coredb.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.scd.coredb.dao.PushStateDao;
import com.scd.coredb.pojo.db.TPushState;
import com.scd.joggle.constant.Type;

@Service
public class PushStateService {
	
	@Autowired
	private PushStateDao pushStateDao;

	public TPushState getOne(String account, long pushId) {
		Specification<TPushState> spec = new Specification<TPushState>() {
    		@Override
    		public Predicate toPredicate(Root<TPushState> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TPushState.ACCOUNT).as(String.class), account));
    			predicates.add(criteriaBuilder.equal(root.get(TPushState.PUSH_ID).as(Long.class), pushId));
    			
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		
		return pushStateDao.findOne(spec);
	}

	public boolean updateHasRead(TPushState tPushState) {
		if (tPushState == null) {
			return false;
		}
		tPushState.setState(Type.PUSH_READ_HAS);
		tPushState.setUpdateTime(System.currentTimeMillis());
		TPushState newTPushState = pushStateDao.save(tPushState);
		if (newTPushState == null) {
			return false;
		}
		
		return true;
	}

	public TPushState read(String account, long pushId) {
		long curTime = System.currentTimeMillis();
		
		TPushState tPushState = getOne(account, pushId);
		if (tPushState == null) {
			tPushState = new TPushState(0, account, pushId, Type.PUSH_READ_HAS, curTime, curTime);
			if (pushStateDao.save(tPushState) == null) {
				return null;
			}
		} else if (tPushState.getState() == Type.PUSH_READ_NO) {
			tPushState.setState(Type.PUSH_READ_HAS);
			tPushState.setUpdateTime(System.currentTimeMillis());
			if (pushStateDao.save(tPushState) == null) {
				return null;
			}
		}
		
		return tPushState;
	}
}
