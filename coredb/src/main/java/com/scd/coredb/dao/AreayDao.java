package com.scd.coredb.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TArea;

@Transactional
public interface AreayDao extends PagingAndSortingRepository<TArea, Long>, JpaSpecificationExecutor<TArea> {

	List<TArea> findByParentId(long parentId);

}
