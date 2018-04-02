package com.scd.admin.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.admin.dao.pojo.TAdmin;

public interface AdminDao extends PagingAndSortingRepository<TAdmin, Long>, JpaSpecificationExecutor<TAdmin> {

	TAdmin findByLoginName(String loginName);
}
