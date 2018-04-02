package com.scd.coredb.dao;

import com.scd.coredb.pojo.db.TOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

@Transactional
public interface OrderDao extends PagingAndSortingRepository<TOrder, Long>, JpaSpecificationExecutor<TOrder> {

	TOrder findByOdd(String odd);

	@Modifying
	@Query(value = " update TOrder t SET t.state = ?1,t.payTime=?2,t.integral= ?3 WHERE t.odd = ?4")
	int updateOrderState(int state,long payTime,long integral, String odd);

}
