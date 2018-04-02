package com.scd.app.feign;

import java.util.List;

import com.scd.sdk.util.pojo.PageInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scd.app.hystrix.HComment;
import com.scd.joggle.pojo.po.CommentAddPo;
import com.scd.joggle.pojo.po.CommentPo;
import com.scd.sdk.util.pojo.Return;

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

    @RequestMapping(value = "/comment/addComment")
    Return<Object> addComment(@RequestBody List<CommentAddPo> commentList);


}
