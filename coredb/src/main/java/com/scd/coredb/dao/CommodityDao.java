package com.scd.coredb.dao;

import com.scd.coredb.pojo.db.TCommodity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

@Transactional
public interface CommodityDao extends PagingAndSortingRepository<TCommodity, Long>, JpaSpecificationExecutor<TCommodity> {

    TCommodity findByShopId(long shopId);
}
