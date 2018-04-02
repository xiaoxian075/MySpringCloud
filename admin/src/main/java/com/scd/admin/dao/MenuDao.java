package com.scd.admin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.admin.dao.pojo.TMenu;

public interface MenuDao extends PagingAndSortingRepository<TMenu, Long>, JpaSpecificationExecutor<TMenu> {

	List<TMenu> findByIsShow(int isShow);

}
