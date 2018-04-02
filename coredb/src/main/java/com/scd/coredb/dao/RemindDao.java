package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TRemind;

public interface RemindDao extends PagingAndSortingRepository<TRemind, Long>, JpaSpecificationExecutor<TRemind> {

}
