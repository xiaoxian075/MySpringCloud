package com.scd.coredb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.coredb.dao.PaySdkDao;
import com.scd.coredb.pojo.db.TPaySdk;

@Service
public class PaySdkService {
	
	@Autowired
	private PaySdkDao paySdkDao;
	
	public TPaySdk getByClientOdd(String clientOdd) {
		if (clientOdd == null || clientOdd.length() == 0) {
			return null;
		}
		TPaySdk tPaySdk = paySdkDao.findByClientOdd(clientOdd);
		if (tPaySdk == null) {
			return null;
		}
		
		return tPaySdk;
	}
	
	public TPaySdk getByOdd(String odd) {
		if (odd == null || odd.length() == 0) {
			return null;
		}
		TPaySdk tPaySdk = paySdkDao.findByOdd(odd);
		if (tPaySdk == null) {
			return null;
		}
		
		return tPaySdk;
	}
	
	public TPaySdk getByOrderOdd(String orderOdd) {
		if (orderOdd == null || orderOdd.length() == 0) {
			return null;
		}
		TPaySdk tPaySdk = paySdkDao.findByOrderOdd(orderOdd);
		if (tPaySdk == null) {
			return null;
		}
		
		return tPaySdk;
	}

	public boolean save(TPaySdk tPaySdk) {
		if (tPaySdk == null) {
			return false;
		}
		TPaySdk newTPaySdk = paySdkDao.save(tPaySdk);
		if (newTPaySdk == null) {
			return false;
		}
		return true;
	}

}
