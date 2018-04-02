package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TPayRecord;

public interface PayRecordDao extends PagingAndSortingRepository<TPayRecord, Long>, JpaSpecificationExecutor<TPayRecord> {

	TPayRecord findByTradeOdd(String tradeOdd);

    TPayRecord findByOrderOdd(String orderOdd);
}
