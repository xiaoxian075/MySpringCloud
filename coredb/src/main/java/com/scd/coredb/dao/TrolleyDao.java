package com.scd.coredb.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TTrolley;

@Transactional
public interface TrolleyDao extends PagingAndSortingRepository<TTrolley, Long>, JpaSpecificationExecutor<TTrolley> {

	List<TTrolley> findByAccount(String account);

	TTrolley findByOdd(String odd);

}
