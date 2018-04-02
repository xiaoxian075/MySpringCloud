package com.scd.mweb.third;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scd.mweb.constant.Constant;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.sdk.util.DesUtil;
import com.scd.sdk.util.HttpUtil;
import com.scd.sdk.util.pojo.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ThirdYft {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	private ThirdYft() {
	}
	private static class ThirdYftFactory {
		private static ThirdYft instance = new ThirdYft();
	}
	public static ThirdYft getInstance() {
		return ThirdYftFactory.instance;
	}
	
	
	private final static String URL_GET_ACCOUNT_BY_LOGIN_AND_MOBILE = "/api/v2/user/loginByMobile";
	private final static String URL_REGISTER_ACCOUNT = "/api/v2/user/registWithDefRef";
	/**
	 * 是否加密，默认固定为1，加密
	 */
	private final static String TERMINAL = "1";
	
	
	private String baseUrl;/* = "http://test-api.ipaye.cn";*/
	private String desKey;/* = "12345678";*/
	
	public void init(String baseUrl, String desKey) {
		this.baseUrl = baseUrl;
		this.desKey = desKey;
	}

	/**
	 * 从云付通获取用户信息
	 * @param account
	 * @return
	 */
	public Return<AccountBo> getAccountInfo(String account, String password) {
		Map<String, String> paramsMap = new HashMap<String, String>(3);
		paramsMap.put("account", account);
		paramsMap.put("password", password);
		paramsMap.put("terminal", TERMINAL);
		
		AccountBo accountBo = null;
		try {
			String url = baseUrl + URL_GET_ACCOUNT_BY_LOGIN_AND_MOBILE;
			String data = HttpUtil.httpPostFprYft(url, paramsMap);
			if (data == null) {
				return Constant.createReturn(ErrorCom.YFT_ERROR);
			}
			data = DesUtil.decryptDueBase64(data, desKey);
			if (data == null) {
				return Constant.createReturn(ErrorCom.YFT_ERROR);
			}
			Return<AccountBo> ret = toAccount(data);
    		if (Return.isErr(ret)) {
    			if (ret != null) {
    				return Constant.createReturn(ret.getCode(), ret.getDesc());
    			} else {
    				return Constant.createReturn(ErrorCom.YFT_ERROR);
    			}
    		}
    		
    		accountBo = ret.getData();
    		if (accountBo == null) {
    			return Constant.createReturn(ErrorCom.YFT_ERROR);
    		}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.YFT_ERROR);
		}
		
		accountBo.setPassword(password);
		return Constant.createReturn(accountBo);
	}

	/**
	 * 向云付通注册账号
	 * @return
	 */
	public Return<Object> register(AccountBo accountBo) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("loginName", accountBo.getLoginName());
		paramsMap.put("mobile", accountBo.getAccount());
		paramsMap.put("password", accountBo.getPassword());
		paramsMap.put("regWay", "9");
		paramsMap.put("defRef", accountBo.getAccount());
		paramsMap.put("referrer", accountBo.getReferee());
		paramsMap.put("terminal", TERMINAL);
		
		try {
			String url = baseUrl + URL_REGISTER_ACCOUNT;
			String data = HttpUtil.httpPostFprYft(url, paramsMap);
			if (data == null) {
				return Constant.createReturn(ErrorCom.YFT_ERROR);
			}
			data = DesUtil.decryptDueBase64(data, desKey);
			if (data == null) {
				return Constant.createReturn(ErrorCom.YFT_ERROR);
			}
			
			Return<AccountBo> ret = toAccount(data);
    		if (Return.isErr(ret)) {
    			if (ret != null) {
    				return Constant.createReturn(ret.getCode(), ret.getDesc());
    			} else {
    				return Constant.createReturn(ErrorCom.YFT_ERROR);
    			}
    		}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.YFT_ERROR);
		}
		
		return Constant.createReturn();
	}

	

	private Return<AccountBo> toAccount(String data) {
		AccountBo accountBo = null;
		JSONObject jsonObject = JSON.parseObject(data);
		Integer is = (Integer)jsonObject.get("is");
		String msg = (String)jsonObject.get("msg");
		if (is != null && is == 1) {
			Object obj = jsonObject.get("data");
			if (obj == null) {
				return Constant.createReturn(ErrorCom.YFT_ERROR);
			}
			jsonObject = JSON.parseObject(obj.toString());
			String loginName = (String) jsonObject.get("loginName");
			String nickName = (String)jsonObject.get("nickName");
			Integer lev = (Integer)jsonObject.get("lev");
			Integer sex = (Integer)jsonObject.get("sex");
			int mySex = Type.SEX_NONE;
			if (sex == 0) {
				mySex = Type.SEX_MAIL;
			} else {
				mySex = Type.SEX_FEMAIL;
			}
			
			accountBo = new AccountBo(0,loginName, loginName, "", nickName, "", mySex, lev, BigDecimal.ZERO, 0L, "", System.currentTimeMillis(), 0L,1,null,1,1, BigDecimal.ZERO, BigDecimal.ZERO);
		} else {
			return Constant.createReturn(ErrorCom.THIRD_ERROR.getCode(), msg);
		}
		
		return Constant.createReturn(accountBo);
	}
	
}
