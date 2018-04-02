package com.scd.coredb.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TShop;

@Transactional
public interface ShopDao extends PagingAndSortingRepository<TShop, Long>, JpaSpecificationExecutor<TShop> {

	TShop findByName(String name);

}
