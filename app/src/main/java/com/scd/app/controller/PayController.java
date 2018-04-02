package com.scd.app.controller;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.*;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.service.PayService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.PayInfoPo;
import com.scd.joggle.pojo.po.PayResultPo;
import com.scd.sdk.util.NetworkUtil;
import com.scd.sdk.util.pojo.PageAcceptMsg;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping(value = "/pay")
public class PayController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Resource
    private PayService payService;

    /**
     * APP请求支付
     */
    @RequestMapping(value = "/requestPay", method = RequestMethod.POST)
    public String requestPay(HttpServletRequest request) {
    	PayInfoPo payInfo = null;
    	try {
	        PayAcceptMsg acceptMsg = Constant.subPack(request,PayAcceptMsg.class);
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
	        String orderOdd = acceptMsg.getOrderOdd();
	        Return<PayInfoPo> ret = payService.requestPay(account, orderOdd);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        payInfo = ret.getData();
		} catch (Exception e) {
    		logger.error(e.getMessage());
    		return Constant.pack(ErrorCom.ERROR);
    	}
        return Constant.pack(payInfo);
    }

    /**
     * 余额支付
     */
    @RequestMapping(value = "/balancePay", method = RequestMethod.POST)
    public String balancePay(HttpServletRequest request) {
    	PayResultPo payInfo = null;
    	try {
	        PayBalanceAcceptMsg acceptMsg = Constant.subPack(request, PayBalanceAcceptMsg.class);
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
	        String orderOdd = acceptMsg.getOrderOdd();
	        String payPassword = acceptMsg.getPayPassword();
	        Return<PayResultPo> ret = payService.balancePay(account,orderOdd,payPassword);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        payInfo = ret.getData();
       	} catch (Exception e) {
    		logger.error(e.getMessage());
    		return Constant.pack(ErrorCom.ERROR);
    	}	 
    	
        return Constant.pack(payInfo);
    }

    /**
     * 余额充值
     */
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public String recharge(HttpServletRequest request) {
    	PayInfoPo payInfo = null;
    	try {
	        RechargeAcceptMsg acceptMsg = Constant.subPack(request, RechargeAcceptMsg.class);
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
	        String odd = acceptMsg.getOdd();
	        BigDecimal amount = acceptMsg.amount();
	        String account = session.getAccount();
	
	        Return<PayInfoPo> ret = payService.recharge(account,odd,amount);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        payInfo = ret.getData();
       	} catch (Exception e) {
    		logger.error(e.getMessage());
    		return Constant.pack(ErrorCom.ERROR);
    	}	 
    	
        return Constant.pack(payInfo);
    }

    /**
     * 查询支付结果
     */
    @RequestMapping(value = "/getPayResult", method = RequestMethod.POST)
    public String getPayResult(HttpServletRequest request) {
    	PayResultPo payResult = null;
    	try {
	        PayResultAcceptMsg acceptMsg = Constant.subPack(request, PayResultAcceptMsg.class);
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
	        String tradeOdd = acceptMsg.getTradeOdd();
	        Return<PayResultPo> ret = payService.getPayResult(account, tradeOdd);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        payResult = ret.getData();
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		return Constant.pack(ErrorCom.ERROR);
    	}	
        return Constant.pack(payResult);
    }

    /**
     * 获取订单明细列表
     */
    @RequestMapping(value = "/getPayList", method = RequestMethod.POST)
    public String getPayList(HttpServletRequest request) {
    	List<PayResultPo> listInfo = null;
    	try {
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
	
	        String account = session.getAccount();
	        int page = acceptMsg.getPage();
	        int size = acceptMsg.getSize();
	        Return<List<PayResultPo>> ret = payService.getPayList(page, size, account);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        listInfo = ret.getData();
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		return Constant.pack(ErrorCom.ERROR);
    	}
    	
        return Constant.pack(listInfo);
    }


    /**
     * 云付通sdk异步通知
     * @param request
     * @param response
     */
    @RequestMapping(value = "/yunpayNotice", method = RequestMethod.POST)
    public void yunpayNotice(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("==================云付通回调=====================");
		PrintWriter out = null;
        try {
            out = response.getWriter();
            String data = NetworkUtil.readFromRequest(request, "UTF-8");
			System.out.println(data);
			if(data != null) {
	            if(payService.yunpayNotice(data)){
	                out.println("SUCCESS");
	            } else {
	            	out.println("FAIL");
	            }
            } else {
            	out.println("FAIL");
            }
        } catch (Exception e) {
        	out.println("FAIL");
        	logger.error(e.getMessage());
        } finally {
        	if (out != null) {
		        out.flush();
		        out.close();
        	}
        }
    }
    
}
