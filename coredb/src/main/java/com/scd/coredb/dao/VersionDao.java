package com.scd.coredb.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TVersion;

public interface VersionDao extends PagingAndSortingRepository<TVersion, Long>, JpaSpecificationExecutor<TVersion> {

	TVersion findByType(int type);

}
