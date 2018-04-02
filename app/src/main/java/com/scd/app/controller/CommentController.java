package com.scd.app.controller;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.CommentAcceptMsg;
import com.scd.app.controller.accept.CommentAddAcceptMsg;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.vo.CommentVo;
import com.scd.app.service.CommentService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
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
        CommentAcceptMsg acceptMsg = Constant.subNoPack(request, CommentAcceptMsg.class);
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
        return Constant.noPack(ret.getData());
    }

    /**
     * 添加评论
     * @param request
     * @return
     */
    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(HttpServletRequest request) {
        CommentAddAcceptMsg acceptMsg = Constant.subNoPack(request, CommentAddAcceptMsg.class);
        if (acceptMsg == null) {
            return Constant.pack(ErrorCom.UNPACK_ERROR);
        }
        if (!acceptMsg.check()) {
            return Constant.pack(ErrorCom.PARSE_ERROR);
        }

        Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
        if (session == null) {
            return Constant.pack(ErrorCom.GET_LOGIN);
        }

        Return<Object> ret = commentService.addComment(acceptMsg);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }

        return Constant.pack();
    }
}
