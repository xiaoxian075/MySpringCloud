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
import org.springframework.transaction.annotation.Transactional;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.AddressDao;
import com.scd.coredb.dao.AreaDao;
import com.scd.coredb.pojo.db.TAddress;
import com.scd.coredb.pojo.db.TArea;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.po.AddressPo;
import com.scd.sdk.util.pojo.Return;

@Service
public class AddressService {
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private AreaDao areaDao;

	public Return<List<TAddress>> findByAccount(String account) {
		List<TAddress> addressList = addressDao.findByAccount(account);
		if (addressList == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		return Constant.createReturn(addressList);
	}

	@Transactional
	public Return<TAddress> add(TAddress tAddress) {
		TArea tArea = areaDao.findOne(tAddress.getAddrId());
		if (tArea == null) {
			return Constant.createReturn(ErrorCom.AREA_NOT_EXIST);
		}
		tAddress.setAddrName(tArea.getFullName());
		
		
		/*Specification<TAddress> spec = new Specification<TAddress>() {
    		@Override
    		public Predicate toPredicate(Root<TAddress> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TAddress.ACCOUNT).as(String.class), tAddress.getAccount()));
    
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		long count = addressDao.count(spec);
		
		if (count == 0) {
			tAddress.setIsDefault(Type.RECEIVE_ADDR_YES);
		} else */if (tAddress.getIsDefault() == Type.RECEIVE_ADDR_YES) {
			if (addressDao.clearDefault(tAddress.getAccount(), Type.RECEIVE_ADDR_NO) < 0) {
				return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
			}
		}
		
		TAddress newTAddress = addressDao.save(tAddress);  
		if (newTAddress == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		
		return Constant.createReturn(newTAddress);
	}
	
	@Transactional
	public Return<TAddress> edit(AddressPo addressPo) {
		TAddress tAddress = addressDao.findOne(addressPo.getId());
		if (tAddress == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		boolean isUpdate = false;
		if (!addressPo.getAccount().equals(tAddress.getAccount())) {
			tAddress.setAccount(addressPo.getAccount());
			isUpdate = true;
		}
		if (!addressPo.getName().equals(tAddress.getName())) {
			tAddress.setName(addressPo.getName());
			isUpdate = true;
		}
		if (!addressPo.getPhone().equals(tAddress.getPhone())) {
			tAddress.setPhone(addressPo.getPhone());
			isUpdate = true;
		}
		if (addressPo.getAddrId() != tAddress.getAddrId()) {
			TArea tArea = areaDao.findOne(addressPo.getAddrId());
			if (tArea == null) {
				return Constant.createReturn(ErrorCom.AREA_NOT_EXIST);
			}
			tAddress.setAddrId(addressPo.getAddrId());
			tAddress.setAddrName(tArea.getFullName());
			isUpdate = true;
		}
		if (!addressPo.getAddrDetail().equals(tAddress.getAddrDetail())) {
			tAddress.setAddrDetail(addressPo.getAddrDetail());
			isUpdate = true;
		}
		if (addressPo.getIsDefault() != tAddress.getIsDefault()) {
			if (addressPo.getIsDefault() == Type.RECEIVE_ADDR_YES) {
				if (addressDao.clearDefault(addressPo.getAccount(), Type.RECEIVE_ADDR_NO) == 0) {
					return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
				}
			}
			tAddress.setIsDefault(addressPo.getIsDefault());
			isUpdate = true;
		}
		
		if (!isUpdate) {
			return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
		}
		
		tAddress.setUpdateTime(System.currentTimeMillis());
		
		TAddress newTAddress = addressDao.save(tAddress);
		if (newTAddress == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		
		return Constant.createReturn(newTAddress);
	}

	public Return<TAddress> del(long id) {
		TAddress tAddress = addressDao.findOne(id);
		if (tAddress == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		addressDao.delete(id);
    	
    	return Constant.createReturn(tAddress);
	}

	@Transactional
	public Return<TAddress> setDefault(long id, int state) {
		TAddress tAddress = addressDao.findOne(id);
		if (tAddress == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		if (state == tAddress.getIsDefault()) {
			return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
		}
		String account = tAddress.getAccount();
		
		if (addressDao.clearDefault(account, Type.RECEIVE_ADDR_NO) == 0) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
		
		tAddress.setIsDefault(state);
		tAddress.setUpdateTime(System.currentTimeMillis());
		TAddress newTAddress = addressDao.save(tAddress);
		if (newTAddress == null) {
			return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
		}
    	
    	return Constant.createReturn(newTAddress);
	}

	public Return<AddressPo> getDefault(String account) {
		Specification<TAddress> spec = new Specification<TAddress>() {
    		@Override
    		public Predicate toPredicate(Root<TAddress> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TAddress.ACCOUNT).as(String.class), account));
    			predicates.add(criteriaBuilder.equal(root.get(TAddress.IS_DEFAULT).as(Integer.class), Type.RECEIVE_ADDR_YES));
    
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		
		TAddress tAddress = addressDao.findOne(spec);
		if (tAddress == null) {
			return Constant.createReturn(ErrorCom.ADDRESS_NO_DEFAULT);
		}
		
		AddressPo po = tAddress.createPo();
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(po);
	}
}
