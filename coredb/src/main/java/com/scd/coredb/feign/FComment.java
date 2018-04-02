package com.scd.coredb.feign;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.CommentDao;
import com.scd.coredb.pojo.db.TComment;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CommentAddPo;
import com.scd.joggle.pojo.po.CommentPo;
import com.scd.sdk.util.GsonUtil;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-03-05.
 */
@RestController
@RequestMapping(value = "/comment")
@Slf4j
public class FComment {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private CommentDao commentDao;

    @RequestMapping(value = "/getAppCommentList")
    public Return<PageInfo<CommentPo>> getCollectList(
                                                      @RequestParam(value = "page") int page,
                                                      @RequestParam(value = "size") int size,
                                                      @RequestParam(value = "goodsId") long goodsId) {
        PageInfo<CommentPo> pPage = null;
        try {
            Specification<TComment> spec = new Specification<TComment>() {
                @Override
                public Predicate toPredicate(Root<TComment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    predicates.add(criteriaBuilder.equal(root.get(TComment.GOODS_ID), goodsId));
                    predicates.add(criteriaBuilder.equal(root.get(TComment.IS_SHOW), 1));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, TComment.COMMENT_TIME);
            Pageable pageable = new PageRequest(page, size, new Sort(order));
            Page<TComment> pageInfo = commentDao.findAll(spec, pageable);
            if (pageInfo == null) {
                return null;
            }
            List<TComment> list = pageInfo.getContent();
            int total = (int) pageInfo.getTotalElements();
            if (list == null) {
                return null;
            }
            List<CommentPo> po = new ArrayList<CommentPo>();
            for (TComment tComment : list) {
                CommentPo commentPo = new CommentPo(
                        tComment.getId(),
                        tComment.getIsAnonymous(),
                        tComment.getAccountId(),
                        tComment.getHeadPicUrl(),
                        tComment.getNickName(),
                        tComment.getContent(),
                        tComment.getCommentTime(),
                        tComment.getPictureUrl()
                );
                po.add(commentPo);
            }
            pPage = new PageInfo<CommentPo>(page, total, po);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn(pPage);
    }



    @RequestMapping(value = "/addComment")
    @Transactional
    public Return<Object> add(@RequestBody List<CommentAddPo> commentList) {
        try {
            System.out.println(GsonUtil.toString(commentList));
            for (CommentAddPo commentAddPo : commentList) {
                TComment tComment = new TComment(
                        0,
                        commentAddPo.getAccountId(),
                        commentAddPo.getOrderId(),
                        commentAddPo.getGoodsId(),
                        commentAddPo.getGoodsDescribe(),
                        commentAddPo.getDescribeStar(),
                        commentAddPo.getContent(),
                        commentAddPo.getIsAnonymous(),
                        commentAddPo.getLogistics(),
                        commentAddPo.getLogisticsStar(),
                        commentAddPo.getService(),
                        commentAddPo.getServiceStar(),
                        commentAddPo.getPictureUrl(),
                        1,//默认显示评论
                        System.currentTimeMillis(),
                        commentAddPo.getAccount(),
                        commentAddPo.getNickName(),
                        commentAddPo.getHeadPicUrl()
                );
                commentDao.save(tComment);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
        return Constant.createReturn();
    }

}
