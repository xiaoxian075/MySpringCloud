package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TPushState;

public interface PushStateDao extends PagingAndSortingRepository<TPushState, Long>, JpaSpecificationExecutor<TPushState> {

}
