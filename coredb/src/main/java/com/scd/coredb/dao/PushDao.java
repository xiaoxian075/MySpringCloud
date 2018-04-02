package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TPush;

public interface PushDao extends PagingAndSortingRepository<TPush, Long>, JpaSpecificationExecutor<TPush> {

}
