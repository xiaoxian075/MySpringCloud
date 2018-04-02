package com.scd.mweb.feign;

import com.scd.joggle.pojo.po.CommentPo;
import com.scd.mweb.hystrix.HComment;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2018-03-05.
 */
@FeignClient(value = "${feign.coredb}", fallback = HComment.class)
public interface FComment {

    @RequestMapping(value = "/comment/getAppCommentList")
    Return<PageInfo<CommentPo>>  getAppCommentList(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "goodsId") long goodsId);


}
