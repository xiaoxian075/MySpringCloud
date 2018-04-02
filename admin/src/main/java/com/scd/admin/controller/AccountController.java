package com.scd.admin.controller;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.AccountSelectAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.AccountVo;
import com.scd.admin.service.AccountService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.AccountSelectParam;
import com.scd.sdk.util.pojo.IdStateAcceptMsg;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Resource
    private AccountService accountService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(HttpServletRequest request) {
        AccountSelectAcceptMsg acceptMsg = Constant.subPack(request, AccountSelectAcceptMsg.class);
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

        AccountSelectParam param = new AccountSelectParam(acceptMsg.getPage(), acceptMsg.getSize(), acceptMsg.getAccount());
        Return<PageInfo<AccountVo>> ret = accountService.getPage(param);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }

    @RequestMapping(value = "/state", method = RequestMethod.POST)
    public String add(HttpServletRequest request) {
        try {
            IdStateAcceptMsg acceptMsg = Constant.subPack(request, IdStateAcceptMsg.class);
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
            int type = acceptMsg.getState();
            accountService.state(id, type);
        } catch (Exception e) {
            return Constant.pack(ErrorCom.ERROR);
        }

        return Constant.pack();
    }
}
