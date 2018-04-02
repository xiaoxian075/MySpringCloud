package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TPlatformBalance;

public interface PlatformBalanceDao extends PagingAndSortingRepository<TPlatformBalance, Long>, JpaSpecificationExecutor<TPlatformBalance> {

	TPlatformBalance findByAccount(String account);

}
