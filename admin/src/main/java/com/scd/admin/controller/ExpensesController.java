package com.scd.admin.controller;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.ExpensesAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.util.ExpensesUtil;
import com.scd.admin.pojo.vo.ExpensesVo;
import com.scd.admin.service.ExpensesService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.ExpensesParam;
import com.scd.sdk.util.pojo.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/expenses")
public class ExpensesController {

    @Resource
    private ExpensesService expensesService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(HttpServletRequest request) {
        PageAcceptMsg acceptMsg = Constant.subPack(request, PageAcceptMsg.class);
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

        int page = acceptMsg.getPage();
        int size = acceptMsg.getSize();
        Return<PageInfo<ExpensesVo>> ret = expensesService.list(page, size);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }

        return Constant.pack(ret.getData());
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest request) {
        ExpensesAcceptMsg acceptMsg = Constant.subPack(request, ExpensesAcceptMsg.class);
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

        ExpensesParam expensesParam = ExpensesUtil.change(acceptMsg);
        Return<ExpensesVo> ret = expensesService.add(expensesParam);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }

        return Constant.pack();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(HttpServletRequest request) {
        ExpensesAcceptMsg acceptMsg = Constant.subPack(request, ExpensesAcceptMsg.class);
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

        ExpensesParam expensesParam = ExpensesUtil.change(acceptMsg);
        Return<Object> ret = expensesService.edit(expensesParam);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }

        return Constant.pack();
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public String del(HttpServletRequest request) {
        IdAcceptMsg acceptMsg = Constant.subPack(request, IdAcceptMsg.class);
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

        long id = acceptMsg.getId();
        Return<Long> ret = expensesService.del(id);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }

        return Constant.pack(ret.getData());
    }

    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
    public String batchDel(HttpServletRequest request) {
        IdlistAcceptMsg acceptMsg = Constant.subPack(request, IdlistAcceptMsg.class);
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

        List<Long> idList = acceptMsg.getIdList();
        Return<List<Long>> ret = expensesService.batchDel(idList);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }

        return Constant.pack(ret.getData());
    }
}
