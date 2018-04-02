package com.scd.coredb.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TAddress;

@Transactional
public interface AddressDao extends PagingAndSortingRepository<TAddress, Long>, JpaSpecificationExecutor<TAddress> {

	List<TAddress> findByAccount(String account);

	@Modifying
    @Query("UPDATE TAddress t SET t.isDefault = ?2 WHERE t.account = ?1")
	int clearDefault(String account, int isDefault);

	//List<TArea> findByParentId(long parentId);

}
