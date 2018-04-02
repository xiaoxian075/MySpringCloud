package com.scd.coredb.dao;

import com.scd.coredb.pojo.db.TAccountLogin;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;


@Transactional
public interface AccountLoginDao extends PagingAndSortingRepository<TAccountLogin, Long>, JpaSpecificationExecutor<TAccountLogin> {

	TAccountLogin findByLoginName(String loginName);
	
	TAccountLogin findByAccount(String account);

	@Modifying
	@Query(value = " update TAccountLogin t SET t.password = ?1  WHERE t.account = ?2")
	int updatePwd(String newPassword,String account);
}
