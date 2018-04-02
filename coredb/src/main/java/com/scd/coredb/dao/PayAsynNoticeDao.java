package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TPayAsynNotice;

public interface PayAsynNoticeDao extends PagingAndSortingRepository<TPayAsynNotice, Long>, JpaSpecificationExecutor<TPayAsynNotice> {

	TPayAsynNotice findByTradeNo(String tradeNo);

}
