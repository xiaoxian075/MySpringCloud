package com.scd.coredb.dao;

import com.scd.coredb.pojo.db.TComment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2018-03-05.
 */
@Transactional
public interface CommentDao  extends PagingAndSortingRepository<TComment, Long>, JpaSpecificationExecutor<TComment> {

//    @Query("select a.*,b.* from t_comment a left join  t_account_info b on  a.account_id = b.id where a.goods_id = :goodsId")
//    List<TComment> getCommentList(@Param("goodsId") int goodsId);
}
