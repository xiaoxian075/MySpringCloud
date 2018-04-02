package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TFinance;


public interface FinanceDao extends PagingAndSortingRepository<TFinance, Long>, JpaSpecificationExecutor<TFinance> {

}
