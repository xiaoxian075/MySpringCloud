package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TPaySdk;

public interface PaySdkDao extends PagingAndSortingRepository<TPaySdk, Long>, JpaSpecificationExecutor<TPaySdk> {

	TPaySdk findByOdd(String odd);
	TPaySdk findByClientOdd(String clientOdd);
	TPaySdk findByOrderOdd(String orderOdd);

}
