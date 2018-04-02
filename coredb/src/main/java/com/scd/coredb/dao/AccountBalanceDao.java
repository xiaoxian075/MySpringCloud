package com.scd.coredb.dao;

import com.scd.coredb.pojo.db.TAccountBalance;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;


@Transactional
public interface AccountBalanceDao extends PagingAndSortingRepository<TAccountBalance, Long>, JpaSpecificationExecutor<TAccountBalance> {

	TAccountBalance findByAccount(String account);

	@Modifying
	@Query(value = " update TAccountBalance t SET t.balance = t.balance - ?1  WHERE t.account = ?2")
	int updateAccountAmount(BigDecimal amount, String account);

    @Modifying
    @Query(value = " update TAccountBalance t SET t.payState = ?1  WHERE t.account = ?2")
    int updateState(int state, String account);

	@Modifying
	@Query(value = " update TAccountBalance t SET t.totalEarnings = t.totalEarnings + ?1  WHERE t.account = ?2")
	int updateAccountTotalEarnings(BigDecimal earnings, String account);


	@Modifying
	@Query(value = " update TAccountBalance t SET t.balance = t.balance + ?1  WHERE t.account = ?2")
	int updateAccountAmountRecharge(BigDecimal amount, String account);
}
