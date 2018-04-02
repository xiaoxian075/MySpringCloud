package com.scd.coredb.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TCommunity;



@Transactional
public interface CommunityDao extends PagingAndSortingRepository<TCommunity, Long>, JpaSpecificationExecutor<TCommunity> {

//	@Query("SELECT t FROM TCommunity t WHERE t.type = ?1 LIMIT ?2,?3")
//	List<TCommunity> pageByType(int type, int page, int limit);

}
