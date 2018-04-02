package com.scd.coredb.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TExpenses;

@Transactional
public interface ExpensesDao extends PagingAndSortingRepository<TExpenses, Long>, JpaSpecificationExecutor<TExpenses> {

}
