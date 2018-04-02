package com.scd.app.controller;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.CollectAcceptMsg;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.service.CollectService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.StatePo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018-03-14.
 */
@RestController
@RequestMapping(value = "/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * 添加收藏
     * @return
     */
    @RequestMapping(value = "/addCollect", method = RequestMethod.POST)
    public String add(HttpServletRequest request) {
        CollectAcceptMsg acceptMsg = Constant.subPack(request, CollectAcceptMsg.class);
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

        long commodityId = acceptMsg.getCommodityId();
        Return<Object> ret = collectService.addCollect(
                session.getAccount(),commodityId);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack();
    }

    /**
     * 取消收藏
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelCollect", method = RequestMethod.POST)
    public String cancelCollect(HttpServletRequest request) {
        CollectAcceptMsg acceptMsg = Constant.subPack(request, CollectAcceptMsg.class);
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

        long commodityId = acceptMsg.getCommodityId();
        Return<Object> ret = collectService.cancelCollect(
                session.getAccount(),commodityId);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack();
    }

    /**
     * 判断该商品是否已收藏
     * @param request
     * @return
     */
    @RequestMapping(value = "/isCollect", method = RequestMethod.POST)
    public String isCollect(HttpServletRequest request) {
        CollectAcceptMsg acceptMsg = Constant.subPack(request, CollectAcceptMsg.class);
        if (acceptMsg == null) {
            return Constant.pack(ErrorCom.UNPACK_ERROR);
        }
        if (!acceptMsg.check()) {
            return Constant.pack(ErrorCom.PARSE_ERROR);
        }

        long commodityId = acceptMsg.getCommodityId();
        String account = acceptMsg.getAccount();

        Return<StatePo> ret = collectService.isCollect(account,commodityId);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }
}
