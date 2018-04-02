package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TRich;

public interface RichDao extends PagingAndSortingRepository<TRich, Long>, JpaSpecificationExecutor<TRich> {

}
