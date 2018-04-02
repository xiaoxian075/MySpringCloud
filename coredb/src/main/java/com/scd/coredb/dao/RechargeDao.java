package com.scd.coredb.dao;

import com.scd.coredb.pojo.db.TRecharge;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

/**
 * Created by Administrator on 2018-03-10.
 */
@Transactional
public interface RechargeDao extends PagingAndSortingRepository<TRecharge, Long>, JpaSpecificationExecutor<TRecharge> {

//    @Modifying
//    @Query(value = " update TPay t SET t.yunpayNoticeState = ?1,t.noticeTime = ?2,t.payTime = ?3 WHERE t.payNumber = ?4")
//    int updateNoticeState(int yunpayNoticeState, long noticeTime,long payTime,String odd);
//
//    TPay findByPayNumber(String payNumber);
}
