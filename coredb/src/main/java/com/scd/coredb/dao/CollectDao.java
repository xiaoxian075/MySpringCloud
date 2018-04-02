package com.scd.coredb.dao;

import com.scd.coredb.pojo.db.TCollect;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018-03-13.
 */
@Transactional
public interface CollectDao extends PagingAndSortingRepository<TCollect, Long>, JpaSpecificationExecutor<TCollect> {

    @Modifying
    @Query(value = " delete from TCollect t  WHERE t.commodityId = ?1 and account =?2")
    int delectCollect(long commodityId, String account);
}
