package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TKd100;


public interface Kd100Dao extends PagingAndSortingRepository<TKd100, Long>, JpaSpecificationExecutor<TKd100> {

	public TKd100 findByName(String name);

	public TKd100 findByCode(String code);

}
