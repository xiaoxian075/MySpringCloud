package com.scd.coredb.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TCommodityAttr;

@Transactional
public interface CommodityAttrDao extends PagingAndSortingRepository<TCommodityAttr, Long>, JpaSpecificationExecutor<TCommodityAttr> {

	List<TCommodityAttr> findByCommodityId(long commodityId);

}
