package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TAccountInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AccountInfoDao extends PagingAndSortingRepository<TAccountInfo, Long>, JpaSpecificationExecutor<TAccountInfo> {

	TAccountInfo findByAccount(String account);

	TAccountInfo findById(long id);

	@Modifying
	@Query(value = " update TAccountInfo t SET t.level = ?1  WHERE t.account = ?2")
	int updateAccountInfo(int memberVip, String account);

	@Modifying
	@Query(value = " update TAccountInfo t SET t.payPassword = ?1  WHERE t.account = ?2")
	int updatePayPwd(String payPwd,String account);

    @Modifying
    @Query(value = " update TAccountInfo t SET t.accountState = ?1  WHERE t.account = ?2")
    int updateState(int state, String account);
}
