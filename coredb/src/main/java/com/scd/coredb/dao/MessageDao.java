package com.scd.coredb.dao;

import com.scd.coredb.pojo.db.TMessage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface MessageDao extends PagingAndSortingRepository<TMessage, Long>, JpaSpecificationExecutor<TMessage> {

    public TMessage findByMsgTitle(String msgTitle);

}
