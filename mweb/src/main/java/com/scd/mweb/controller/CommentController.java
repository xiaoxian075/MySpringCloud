package com.scd.mweb.controller;

import com.scd.joggle.constant.ErrorCom;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.controller.accept.CommentAcceptMsg;
import com.scd.mweb.pojo.vo.CommentVo;
import com.scd.mweb.service.CommentService;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by Administrator on 2018-03-05.
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 获取评论列表
     * @return
     */
    @RequestMapping(value = "/getCommentList", method = RequestMethod.POST)
    public String getCommentList(HttpServletRequest request) {
        CommentAcceptMsg acceptMsg = Constant.subPack(request, CommentAcceptMsg.class);
        if (acceptMsg == null) {
            return Constant.pack(ErrorCom.UNPACK_ERROR);
        }

        if (!acceptMsg.check()) {
            return Constant.pack(ErrorCom.PARSE_ERROR);
        }

        long goodsId = acceptMsg.getGoodsId();
        int page = acceptMsg.getPage();
        int size = acceptMsg.getSize();

        Return<PageInfo<CommentVo>> ret = commentService.getCommentList(page, size, goodsId);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }
}
