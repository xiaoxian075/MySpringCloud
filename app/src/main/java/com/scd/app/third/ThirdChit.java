package com.scd.app.third;

import java.util.List;

import com.scd.app.third.sms.ChuangLanSmsUtil;



public class ThirdChit {
	//private static Logger logger = LoggerFactory.getLogger(ThirdChit.class);

    private ThirdChit() {
    }
    private static class ThirdChitFactory {
        private static ThirdChit instance = new ThirdChit();
    }
    public static ThirdChit getInstance() {
        return ThirdChitFactory.instance;
    }
    
    /**
     * 请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
     */
    private String clUrl;
    /**
     * 用户平台API账号(非登录账号,示例:N1234567)
     */
    private String clAccount;
    /**
     * 用户平台API密码(非登录密码)
     */
    private String clPassword;
    
    
    
    public void init(String clUrl, String clAccount, String clPassword) {
    	this.clUrl = clUrl;
    	this.clAccount = clAccount;
    	this.clPassword = clPassword;
    }
  
  private final static String SMS_MODEL_REGISTER = "【云智居】欢迎加入云智居，您的验证码是：##";
  private final static String SMS_MODEL_RESETPWD = "【云智居】您正在找回云智居登录密码，验证码##，为了保护您的账号安全，请勿告知任何人验证码。";

    /**
     * 发送注册验证码
     *
     * @param phoneList
     * @param code
     */
    public boolean sendRegister(String phone, String code) {
    	String msg = assembleModel(SMS_MODEL_REGISTER, code);
        //String phone = String.join(",", phoneList);
        if (msg == null || phone == null) {
        	return false;
        }
        return ChuangLanSmsUtil.sendMsg(clUrl, clAccount, clPassword, msg, phone);
    }
    
    /**
     * 发送注册验证码
     *
     * @param phoneList
     * @param code
     */
    public boolean sendRegister(List<String> phoneList, String code) {
    	String msg = assembleModel(SMS_MODEL_REGISTER, code);
        String phone = String.join(",", phoneList);
        if (msg == null || phone == null) {
        	return false;
        }
        return ChuangLanSmsUtil.sendMsg(clUrl, clAccount, clPassword, msg, phone);
    }

    /**
     * 发送找回密码验证码
     *
     * @param phoneList
     * @param code
     * @return
     */
    public boolean sendResetPwd(List<String> phoneList, String code) {
    	String msg = assembleModel(SMS_MODEL_RESETPWD, code);
        String phone = String.join(",", phoneList);
        if (msg == null || phone == null) {
        	return false;
        }
        return ChuangLanSmsUtil.sendMsg(clUrl, clAccount, clPassword, msg, phone);
    }
    
    /**
     * 发送找回密码验证码
     *
     * @param phoneList
     * @param code
     * @return
     */
    public boolean sendResetPwd(String phone, String code) {
    	String msg = assembleModel(SMS_MODEL_RESETPWD, code);
        //String phone = String.join(",", phoneList);
        if (msg == null || phone == null) {
        	return false;
        }
        return ChuangLanSmsUtil.sendMsg(clUrl, clAccount, clPassword, msg, phone);
    }
    
    
    
    
    
    
    
    
    private static String assembleModel(String model, String... args) {
  	  if (args == null || args.length == 0) {
  		  return model;
  	  }
  	  
  	  String msg = model;
  	  for (String arg : args) {
  		  msg = msg.replaceFirst("##", arg);
  	  }
  	  return msg;
    }
}


