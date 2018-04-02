package com.scd.app.controller;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.AccountAcceptMsg;
import com.scd.app.controller.accept.PayPwdAcceptMsg;
import com.scd.app.controller.accept.UpdateAccountAcceptMsg;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.vo.AccountInfoVo;
import com.scd.app.pojo.vo.CollectVo;
import com.scd.app.pojo.vo.MemberVo;
import com.scd.app.service.AccountService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.AccountPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import com.scd.sdk.util.pojo.ZeroAcceptMsg;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 我的模块
 */
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    @Resource
    private AccountService accountService;

    /**
     * 获取账户信息
     * @return
     */
    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    public String getInfo(HttpServletRequest request) {
        AccountAcceptMsg acceptMsg = Constant.subPack(request, AccountAcceptMsg.class);
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

        Return<AccountInfoVo> ret = accountService.getInfo(session.getAccount());
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }
    /**
     * 获取累计收益
     * @return
     */
    @RequestMapping(value = "/getTotalEarnings", method = RequestMethod.POST)
    public String getTotalEarnings(HttpServletRequest request) {
    	AccountAcceptMsg acceptMsg = Constant.subPack(request, AccountAcceptMsg.class);
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
    	Return<AccountInfoVo> ret = accountService.getTotalEarnings(session.getAccount());
    	if (Return.isErr(ret)) {
    		return Constant.pack(ret);
    	}
    	return Constant.pack(ret.getData());
    }

    /**
     * 获取余额
     * @return
     */
    @RequestMapping(value = "/getBalance", method = RequestMethod.POST)
    public String getBalance(HttpServletRequest request) {
    	AccountAcceptMsg acceptMsg = Constant.subPack(request, AccountAcceptMsg.class);
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
    	Return<AccountInfoVo> ret = accountService.getBalance(session.getAccount());
    	if (Return.isErr(ret)) {
    		return Constant.pack(ret);
    	}
    	return Constant.pack(ret.getData());
    }

    /**
     * 获取推荐人列表
     * @return
     */
    @RequestMapping(value = "/getRefereeList", method = RequestMethod.POST)
    public String getRefereeList(HttpServletRequest request) {
        AccountAcceptMsg acceptMsg = Constant.subPack(request, AccountAcceptMsg.class);
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
        String account = acceptMsg.getAccount();
        Return<PageInfo<AccountInfoVo>> ret = accountService.getRefereeList(account,page,size);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }

    /**
     * 获取收藏列表
     * @return
     */
    @RequestMapping(value = "/getCollectList", method = RequestMethod.POST)
    public String getCollectList(HttpServletRequest request) {
        AccountAcceptMsg acceptMsg = Constant.subPack(request, AccountAcceptMsg.class);
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
        Return<PageInfo<CollectVo>> ret = accountService.getCollectList(session.getAccount(),page,size);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }

    /**
     * 我的会员
     * @return
     */
    @RequestMapping(value = "/myMember", method = RequestMethod.POST)
    public String myMember(HttpServletRequest request) {
        ZeroAcceptMsg acceptMsg = Constant.subPack(request, ZeroAcceptMsg.class);
        if (acceptMsg == null) {
            return Constant.pack(ErrorCom.UNPACK_ERROR);
        }
//        if (!acceptMsg.check()) {
//            return Constant.pack(ErrorCom.PARSE_ERROR);
//        }

        Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
        if (session == null) {
            return Constant.pack(ErrorCom.GET_LOGIN);
        }
        Return<MemberVo> ret = accountService.myMember(session.getAccount());
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }

    /**
     * 设置支付密码
     * @return
     */
    @RequestMapping(value = "/setPayPwd", method = RequestMethod.POST)
    public String setPayPwd(HttpServletRequest request) {
        PayPwdAcceptMsg acceptMsg = Constant.subPack(request, PayPwdAcceptMsg.class);
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
        String payPwd = acceptMsg.getPayPwd();
        Return<Object> ret = accountService.setPayPwd(session.getAccount(),payPwd);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }

    /**
     * 修改支付密码
     * @return
     */
    @RequestMapping(value = "/updatePayPwd", method = RequestMethod.POST)
    public String updatePayPwd(HttpServletRequest request) {
        PayPwdAcceptMsg acceptMsg = Constant.subPack(request, PayPwdAcceptMsg.class);
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
        String payPwd = acceptMsg.getPayPwd();
        String oldPayPwd = acceptMsg.getOldPayPwd();
        Return<Object> ret = accountService.updatePayPwd(session.getAccount(),payPwd,oldPayPwd);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }

    /**
     * 验证旧支付密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/verifyOldPayPwd", method = RequestMethod.POST)
    public String verifyOldPayPwd(HttpServletRequest request) {
        PayPwdAcceptMsg acceptMsg = Constant.subPack(request, PayPwdAcceptMsg.class);
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
        String oldPayPwd = acceptMsg.getOldPayPwd();
        Return<Object> ret = accountService.verifyOldPayPwd(session.getAccount(),oldPayPwd);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }

    /**
     * 修改账户信息
     * @return
     */
    @RequestMapping(value = "/updateAccountInfo", method = RequestMethod.POST)
    public String updateAccountInfo(HttpServletRequest request) {
        UpdateAccountAcceptMsg acceptMsg = Constant.subPack(request, UpdateAccountAcceptMsg.class);
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
        String account = session.getAccount();
        String nickName = acceptMsg.getNickName();
        String headPic = acceptMsg.getHeadPic();
        int sex = acceptMsg.getSex();

        Return<AccountPo> ret = accountService.updateAccountInfo(account,nickName,headPic,sex);
        if (Return.isErr(ret)) {
            return Constant.pack(ret);
        }
        return Constant.pack(ret.getData());
    }
}
