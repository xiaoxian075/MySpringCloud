package com.scd.coredb.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.scd.coredb.pojo.db.TCarousel;



@Transactional
public interface CarouselDao extends PagingAndSortingRepository<TCarousel, Long>, JpaSpecificationExecutor<TCarousel> {

	List<TCarousel> findByType(int type);

//	@Query("SELECT t FROM TCarousel t WHERE t.type = ?1")
//	List<TCarousel> findByType(int type);

}
