package com.scd.coredb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TShopActivity;

public interface ShopActivityDao extends PagingAndSortingRepository<TShopActivity, Long>, JpaSpecificationExecutor<TShopActivity> {

	List<TShopActivity> findByShopId(long shopId);

}
