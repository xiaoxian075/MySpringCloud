package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TOrderMergePay;

public interface OrderMergePayDao extends PagingAndSortingRepository<TOrderMergePay, Long>, JpaSpecificationExecutor<TOrderMergePay>{

	TOrderMergePay findByOdd(String odd);

}
