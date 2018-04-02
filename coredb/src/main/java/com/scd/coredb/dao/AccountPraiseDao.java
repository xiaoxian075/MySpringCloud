package com.scd.coredb.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TAccountPraise;



@Transactional
public interface AccountPraiseDao extends PagingAndSortingRepository<TAccountPraise, Long>, JpaSpecificationExecutor<TAccountPraise> {

	List<TAccountPraise> findByAccount(String account);

//	@Modifying(clearAutomatically = true)
//	@Query("SELECT t FROM TAccountPraise t WHERE t.account = (?1) AND t.communityId = (?2)")
//	TAccountPraise selectByAC(String account, long communityId);

}
