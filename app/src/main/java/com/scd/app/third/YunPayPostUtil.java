//package com.scd.app.third;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.scd.app.constant.ErrorStatus;
//import com.scd.joggle.pojo.bo.AccountBo;
//import com.scd.sdk.util.*;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.apache.log4j.Logger;
//
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.Date;
//import java.util.HashMap;
//
///**
// * 云支付接口
// */
//public class YunPayPostUtil {
//    private static Logger LOGGER = Logger.getLogger(YunPayPostUtil.class);
//    
//    public static String YFZB_ACCOUNT ="19";
//    public static String CLIENT_ID ="yszb";
//    private final static String desKey = "12345678";
//    private final static String REG_WAY = "9";
//
//    private final static String CHECK_LOGIN_NAME_EXIST = "http://test-api.ipaye.cn/api/v2/user/existUserByLoginName";
//    private final static String CHECK_LOGIN_PASSWORD = "http://test-api.ipaye.cn/api/v2/user/loginByMobile";
//    private final static String REGISTER_YUNPAY_ACCOUNT = "http://test-api.ipaye.cn/api/v2/user/registWithDefRef";
//    private final static String YUNPAY_PAY_SDK = "http://test-api.ipaye.cn/yunpay_service/api/oauth/payOrder/subPlatformOrderPay";
//    private final static String CHECK_ORDER_PAY_STATE = "http://test-api.ipaye.cn/api/v2/payorder/validatePay";
//
//    
//    /**
//     * 注册云付通帐户
//     * @param account
//     * @return
//     */
//    public static ResultUtil insertYunpayAccount(AccountBo accountBo){
//        try{
//            String data = YunPayPostUtil.yunRegist(accountBo.getLoginName(),accountBo.getAccount()/*accountBo.getMobile()*/,accountBo.getPassword(),REG_WAY,accountBo.getReferee());
//            JSONObject jsonObject = yunDataToJSONObject(data);
//            if(jsonObject != null){
//                if("1".equals(jsonObject.getString("is"))){
//                    return ResultUtil.suc(ErrorStatus.SUCCESS.getDesc());
//                }
//                String msg = jsonObject.getString("msg");
//                return ResultUtil.fail(msg);
//            }
//        }catch (Exception e){
//            LOGGER.error("e",e);
//        }
//        return ResultUtil.fail(ErrorStatus.REGISTER_FAIL.getDesc());
//    }
//    
//    
////    /**
////     * 组合云付通APK支付数据
////     */
////    public static Map getYunPayApkPayData(String loginName , TOrderPay orderPay){
////        return getYunPayApkPayDataV2(loginName,orderPay);
////    }
////
////    private static Map getYunPayApkPayDataV2(String loginName , TOrderPay orderPay){
////        Map map = null;
////        try{
////            String act = "";
////            if(OrderPay.PAY_ACT.RECHARGE == orderPay.getPayAct()){
////                act = "3"; // 充值
////            }else{
////                act = "6"; // 购物
////            }
////            map = new HashMap<>();
////            map.put("act",act);//动作
////            map.put("amount",orderPay.getPayAmount());//支付金额
////            map.put("clientId",CLIENT_ID);//授权商户ID
////            map.put("extData","AA");//扩展字段
////            map.put("loginName",loginName);//支付帐号
////            map.put("merchantLoginName",YFZB_ACCOUNT);//收款帐号
////            map.put("orderDesc","备注");//备注
////            map.put("orderId",orderPay.getPayNumber());//订单号
////            map.put("timeoutTime",FunUtil.getFormatDate(FunUtil.dateAddSecond(new Date(),900),"yyyyMMddHHmmss"));//订单超时时间
////            map.put("tradeTime",FunUtil.getSysDate("yyyyMMddHHmmss"));//交易时间
////            LOGGER.info(FunUtil.mapToString(map));
////            map.put("putMoney",orderPay.getPayAmount());//保留字段
////            map.put("putOrderId",orderPay.getPayNumber());//保留字段
////
////            //act&amount&clientId&extData&loginName&merchantLoginName&orderDesc&orderId&timeoutTime&tradeTime&key
////            MapGetterTool mapGetterTool = new MapGetterTool(map);
////            StringBuilder stringBuilder = new StringBuilder();
////            stringBuilder.append(mapGetterTool.getString("act")).append("&")
////                    .append(FunUtil.fenToYuan(mapGetterTool.getBigDecimal("amount"))).append("&")
////                    .append(mapGetterTool.getString("clientId")).append("&")
////                    .append(mapGetterTool.getString("extData")).append("&")
////                    .append(mapGetterTool.getString("loginName")).append("&")
////                    .append(mapGetterTool.getString("merchantLoginName")).append("&")
////                    .append(mapGetterTool.getString("orderDesc")).append("&")
////                    .append(mapGetterTool.getString("orderId")).append("&")
////                    .append(mapGetterTool.getString("timeoutTime")).append("&")
////                    .append(mapGetterTool.getString("tradeTime")).append("&").append("cPxZvH4P^mY1pwuvWN!BVdrB5C!sp&ai");
////
////            String sign = MD5Util.encodeByMD5Base64(stringBuilder.toString());
////            map.put("sign",sign);
////            LOGGER.info("sign原值:".concat(stringBuilder.toString()).concat("==sign后:".concat(sign)));
////        }catch (Exception e){
////            LOGGER.error("e",e);
////        }
////        return map;
////    }
//
//    /**
//     * 调用云付通接口 检测订单 是否已付款
//     */
//    public static String checkYunPayPayState(HashMap<String,String> paraMap){
//        HcRequestData hrd = HttpClientUtil.postUrl(CHECK_ORDER_PAY_STATE,paraMap);
//        if(hrd.getStateCode() == 200){
//            String ResultUtilData = "";
//            try {
//                ResultUtilData = DesNewUtil.decrypt(hrd.getResult(), desKey);
//                LOGGER.info("接收数据:"+ResultUtilData);
//                if(ResultUtilData!=null){
//                    JSONObject jsonObject = JSONObject.parseObject(ResultUtilData);
//                    return jsonObject.getString("is");
//                }
//            }  catch (Exception e) {
//                LOGGER.error("e",e);
//            }
//        }
//        return "0";
//    }
//
//    public static JSONObject yunDataToJSONObject(String ciphertext){
//        JSONObject jsonObject = null;
//        try{
//            String data = DesNewUtil.decrypt(ciphertext,desKey);
//            LOGGER.error(data);
//            jsonObject = JSON.parseObject(data);
//        }catch (Exception e){
//            LOGGER.error("e",e);
//        }
//        return jsonObject;
//    }
//
//    public static JSONObject yunDataToJSONObjectNew(String ciphertext){
//        JSONObject jsonObject = null;
//        try{
//            String data = DesNewUtil.decrypt(ciphertext,"9TFLemliVX@bfpiYS2dzwRu#FJjdc&BO");
//            LOGGER.error(data);
//            jsonObject = JSON.parseObject(data);
//        }catch (Exception e){
//            LOGGER.error("e",e);
//        }
//        return jsonObject;
//    }
//
//    /**
//     * 检查云付通登入名是否存在
//     */
//    public static boolean checkLoginNameExtis(String loginName){
//        try{
//            String data = yunLoginName(loginName);
//            JSONObject jsonObject = yunDataToJSONObject(data);
//            if(jsonObject != null){
//                if("0".equals(jsonObject.getString("is"))){
//                    return true;
//                }
//            }
//        }catch (Exception e){
//
//        }
//        return false;
//    }
//
//    /**
//     * 登入云付通
//     */
//    public static ResultUtil loginYunPayCheck(String loginName, String password){
//        try{
//            String data = yunLogin(loginName,password);
//            JSONObject jsonObject = yunDataToJSONObject(data);
//            if(jsonObject != null){
//                if("1".equals(jsonObject.getString("is"))){
//                    return ResultUtil.suc(jsonObject, ErrorStatus.SUCCESS.getDesc());
//                }
//                String msg = jsonObject.getString("msg");
//                return ResultUtil.fail(msg);
//            }
//        }catch (Exception e){
//
//        }
//        return ResultUtil.fail(ErrorStatus.YUN_PAY_LOGIN_ERROR.getDesc());
//    }
//
//
//
//    /**
//     * get方式
//     */
//    public static String getHttp(String ip) {
//
//        String responseMsg = "";
//        String name = URLEncoder.encode("实践组");
//        String url = "http://toupiao.qzl0594.com/?app_act=vote&id=1&itemid=1&group=" + name;
//        String html = null;
//        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
//        HttpPost httpPost = new HttpPost(ip);
//        HttpGet httpget = new HttpGet(url);// 以get方式请求该URL
//        httpget.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_2 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B146 MicroMessenger/5.0");
//        try {
//
//////        getMethod.setRequestHeader("X-Forwarded-For", ip);
//            httpget.setHeader("X-Real-IP", ip);
//            httpget.setHeader("X-Forwarded-For", ip);
//            httpget.setHeader("Remoteaddr", ip);
//            HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
//            int resStatu = responce.getStatusLine().getStatusCode();// 返回码
//            if (resStatu == org.apache.http.HttpStatus.SC_OK) {// 200正常 其他就不对
//                // 获得相应实体
//                HttpEntity entity = responce.getEntity();
//                if (entity != null) {
//                    html = new String(EntityUtils.toString(entity));// 获得html源代码
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("访问【" + url + "】出现异常!");
//            e.printStackTrace();
//        } finally {
//            httpClient.getConnectionManager().shutdown();
//        }
//        return html;
//    }
//
//    /**
//     * 云支付--新 post方式
//     *
//     * @return
//     */
//    public static JSONObject yunPayNew(String fromUserId, String amount, String payPwd, String orderId, String act) {
//        HttpClient httpClient = new HttpClient();
//        // 定义并实例化客户端链接对象-postMethod
//        PostMethod postMethod = new PostMethod(YUNPAY_PAY_SDK);
//        String ResultUtil="";
//        try {
//            // 设置http的头
////            httpClient.getParams().setContentCharset("UTF-8");
//            postMethod.setRequestHeader("ContentType",
//                    "application/x-www-form-urlencoded;charset=UTF-8");
//            postMethod.getParams().setContentCharset("UTF-8");
//            // 填入各个表单域的值
//                JSONObject dataJson = new JSONObject();
//				String startTime = FunUtil.getFormatDate(new Date(),"yyyyMMddHHmmss");
//				String endTime = FunUtil.getTimeByHour(1,"yyyyMMddHHmmss");
//				dataJson.put("version","1.0");
//				dataJson.put("orderId",orderId);
//				dataJson.put("loginName",fromUserId);
//				dataJson.put("payPwd",payPwd);
//				dataJson.put("amount",String.valueOf(amount));
//				dataJson.put("tradeTime",startTime);
//				dataJson.put("timeoutTime",endTime);
//				dataJson.put("merchantLoginName",YFZB_ACCOUNT);
//				dataJson.put("orderDesc","云商珠宝饰购物");
//				dataJson.put("act",act);
//				String sign =act+"&"+String.valueOf(amount)+"&"+fromUserId+"&"+YFZB_ACCOUNT+"&云商珠宝饰购物"+"&"+orderId+"&"+payPwd+"&"+endTime+"&"+startTime+"&1.0&cPxZvH4P^mY1pwuvWN!BVdrB5C!sp&ai";
//				dataJson.put("sign", MD5Util.encodeByMD5(Base64Util.encode(sign.getBytes("UTF-8"))));
//				String dataStr = DesNewUtil.encrypt(dataJson.toString(),"9TFLemliVX@bfpiYS2dzwRu#FJjdc&BO");
//            NameValuePair[] data = {new NameValuePair("clientId", "yszb"),
//                    new NameValuePair("data", dataStr)};
//            // 将表单的值放入postMethod中
//            postMethod.setRequestBody(data);
//            // 定义访问地址的链接状态
//            int statusCode = 0;
//            try {
//                // 客户端请求url数据
//                statusCode = httpClient.executeMethod(postMethod);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // 请求成功状态-200
//            if (statusCode == HttpStatus.SC_OK) {
//                try {
//                    ResultUtil = postMethod.getResponseBodyAsString();
//                    LOGGER.info("PayReturn:" + ResultUtil);
//                    System.out.println(ResultUtil);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("请求返回状态：" + statusCode);
//                LOGGER.info("PayReturn:" + statusCode);
//
//            }
//        } catch (Exception e) {
//            LOGGER.info("PayReturn:" + e.getMessage());
//        } finally {
//            // 释放链接
//            postMethod.releaseConnection();
//            httpClient.getHttpConnectionManager().closeIdleConnections(0);
//        }
//        return yunDataToJSONObjectNew(ResultUtil);
//    }
//    /**
//     * 云支付 验证支付密码 post方式
//     *
//     * @return
//     */
//    public static String yunPayPwd(String url, String userId, String payPwd) {
//        HttpClient httpClient = new HttpClient();
//        // 定义并实例化客户端链接对象-postMethod
//        PostMethod postMethod = new PostMethod(url);
//        String ResultUtil="";
//        try {
//            // 设置http的头
//            postMethod.setRequestHeader("ContentType",
//                    "application/x-www-form-urlencoded;charset=UTF-8");
//            // 填入各个表单域的值
//            NameValuePair[] data = {new NameValuePair("loginName", userId),new NameValuePair("payPwd", payPwd),new NameValuePair("terminal", "1")};
//            // 将表单的值放入postMethod中
//            postMethod.setRequestBody(data);
//            // 定义访问地址的链接状态
//            int statusCode = 0;
//            try {
//                // 客户端请求url数据
//                statusCode = httpClient.executeMethod(postMethod);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // 请求成功状态-200
//            if (statusCode == HttpStatus.SC_OK) {
//                try {
//                    ResultUtil = postMethod.getResponseBodyAsString();
//                    System.out.println(ResultUtil);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("请求返回状态：" + statusCode);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // 释放链接
//            postMethod.releaseConnection();
//            httpClient.getHttpConnectionManager().closeIdleConnections(0);
//        }
//        return ResultUtil;
//    }
//    /**
//     * 云支付注册 post方式
//     *
//     * @return
//     */
//    public static String yunRegist(String loginName, String mobile,String password,String regWay,String referrer) {
//        HttpClient httpClient = new HttpClient();
//        // 定义并实例化客户端链接对象-postMethod
//        PostMethod postMethod = new PostMethod(REGISTER_YUNPAY_ACCOUNT);
//        String ResultUtil="";
//        try {
//            // 设置http的头
//            postMethod.setRequestHeader("ContentType",
//                    "application/x-www-form-urlencoded;charset=UTF-8");
//            postMethod.getParams().setContentCharset("UTF-8");
//            // 填入各个表单域的值
//            NameValuePair[] data = {new NameValuePair("loginName", loginName),new NameValuePair("mobile", mobile),
//                    new NameValuePair("password", password),new NameValuePair("regWay", regWay),new NameValuePair("defRef",YFZB_ACCOUNT),
//                    new NameValuePair("referrer",referrer),new NameValuePair("terminal", "1")};
//            // 将表单的值放入postMethod中
//            postMethod.setRequestBody(data);
//            // 定义访问地址的链接状态
//            int statusCode = 0;
//            try {
//                // 客户端请求url数据
//                statusCode = httpClient.executeMethod(postMethod);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // 请求成功状态-200
//            if (statusCode == HttpStatus.SC_OK) {
//                try {
//                    ResultUtil = postMethod.getResponseBodyAsString();
//                    System.out.println(ResultUtil);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("请求返回状态：" + statusCode);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // 释放链接
//            postMethod.releaseConnection();
//            httpClient.getHttpConnectionManager().closeIdleConnections(0);
//        }
//        return ResultUtil;
//    }
//    /**
//     * 云支付 post方式
//     *
//     * @return
//     */
//    public static String yunLogin(String account, String passWord) {
//        HttpClient httpClient = new HttpClient();
//        // 定义并实例化客户端链接对象-postMethod
//        PostMethod postMethod = new PostMethod(CHECK_LOGIN_PASSWORD);
//        String ResultUtil="";
//        try {
//            // 设置http的头
//            postMethod.setRequestHeader("ContentType",
//                    "application/x-www-form-urlencoded;charset=UTF-8");
//            // 填入各个表单域的值
//            NameValuePair[] data = {new NameValuePair("account", account),new NameValuePair("password", passWord),new NameValuePair("terminal", "1")};
//            // 将表单的值放入postMethod中
//            postMethod.setRequestBody(data);
//            // 定义访问地址的链接状态
//            int statusCode = 0;
//            try {
//                // 客户端请求url数据
//                statusCode = httpClient.executeMethod(postMethod);
//            } catch (Exception e) {
//                LOGGER.error(e);
//            }
//            // 请求成功状态-200
//            if (statusCode == HttpStatus.SC_OK) {
//                try {
//                    ResultUtil = postMethod.getResponseBodyAsString();
//                } catch (IOException e) {
//                    LOGGER.error(e);
//                }
//            } else {
//                System.out.println("请求返回状态：" + statusCode);
//            }
//        } catch (Exception e) {
//            LOGGER.error(e);
//        } finally {
//            // 释放链接
//            postMethod.releaseConnection();
//            httpClient.getHttpConnectionManager().closeIdleConnections(0);
//        }
//        return ResultUtil;
//    }
//    /**
//     * 云支付验证登录名是否存在 post方式
//     * @return
//     */
//    public static String yunLoginName(String account) {
//        HttpClient httpClient = new HttpClient();
//        // 定义并实例化客户端链接对象-postMethod
//        PostMethod postMethod = new PostMethod(CHECK_LOGIN_NAME_EXIST);
//        String ResultUtil="";
//        try {
//            // 设置http的头
//            postMethod.setRequestHeader("ContentType",
//                    "application/x-www-form-urlencoded;charset=UTF-8");
//            // 填入各个表单域的值
//            NameValuePair[] data = {new NameValuePair("account", account),new NameValuePair("terminal", "1")};
//            // 将表单的值放入postMethod中
//            postMethod.setRequestBody(data);
//            // 定义访问地址的链接状态
//            int statusCode = 0;
//            try {
//                // 客户端请求url数据
//                statusCode = httpClient.executeMethod(postMethod);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // 请求成功状态-200
//            if (statusCode == HttpStatus.SC_OK) {
//                try {
//                    ResultUtil = postMethod.getResponseBodyAsString();
//                    System.out.println(ResultUtil);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("请求返回状态：" + statusCode);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // 释放链接
//            postMethod.releaseConnection();
//            httpClient.getHttpConnectionManager().closeIdleConnections(0);
//        }
//        return ResultUtil;
//
//
//    }
//    /**
//     * 云支付 post方式
//     *
//     * @return
//     */
//    public static String yunPassWord(String url, String loginName) {
//        HttpClient httpClient = new HttpClient();
//        // 定义并实例化客户端链接对象-postMethod
//        PostMethod postMethod = new PostMethod(url);
//        String ResultUtil="";
//        try {
//            // 设置http的头
//            postMethod.setRequestHeader("ContentType",
//                    "application/x-www-form-urlencoded;charset=UTF-8");
//            // 填入各个表单域的值
//            NameValuePair[] data = {new NameValuePair("loginName", loginName),new NameValuePair("terminal", "1")};
//            // 将表单的值放入postMethod中
//            postMethod.setRequestBody(data);
//            // 定义访问地址的链接状态
//            int statusCode = 0;
//            try {
//                // 客户端请求url数据
//                statusCode = httpClient.executeMethod(postMethod);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // 请求成功状态-200
//            if (statusCode == HttpStatus.SC_OK) {
//                try {
//                    ResultUtil = postMethod.getResponseBodyAsString();
//                    System.out.println(ResultUtil);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("请求返回状态：" + statusCode);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // 释放链接
//            postMethod.releaseConnection();
//            httpClient.getHttpConnectionManager().closeIdleConnections(0);
//        }
//        return ResultUtil;
//    }
//
//
//    /**
//     * 云支付 post方式  充值
//     *
//     * @return
//     */
//    public static String insertYunRecharge(String url, String fromUserId, String amount, String payPwd, String productName,String imei,String lng,String lat,String orderId) {
//
//        HttpClient httpClient = new HttpClient();
//        // 定义并实例化客户端链接对象-postMethod
//        PostMethod postMethod = new PostMethod(url);
//        String ResultUtil="";
//        try {
//            // 设置http的头
//            postMethod.setRequestHeader("ContentType",
//                    "application/x-www-form-urlencoded;charset=UTF-8");
//            postMethod.getParams().setContentCharset("UTF-8");
//            // 填入各个表单域的值
//            NameValuePair[] data = {new NameValuePair("loginName", fromUserId),
//                    new NameValuePair("amount", amount),new NameValuePair("toLoginName", YFZB_ACCOUNT),
//                    new NameValuePair("payPwd", payPwd),new NameValuePair("productName", productName)
//                    ,new NameValuePair("imei", imei),new NameValuePair("lng", lng),new NameValuePair("lat", lat),new NameValuePair("type", "1"),new NameValuePair("terminal", "1"),new NameValuePair("orderId",orderId)};
//            // 将表单的值放入postMethod中
//            postMethod.setRequestBody(data);
//            // 定义访问地址的链接状态
//            int statusCode = 0;
//            try {
//                // 客户端请求url数据
//                statusCode = httpClient.executeMethod(postMethod);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // 请求成功状态-200
//            if (statusCode == HttpStatus.SC_OK) {
//                try {
//                    ResultUtil = postMethod.getResponseBodyAsString();
//                    LOGGER.info("PayReturn:" + ResultUtil);
//                    System.out.println(ResultUtil);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("请求返回状态：" + statusCode);
//                LOGGER.info("PayReturn:" + statusCode);
//
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            LOGGER.info("PayReturn:" + e.getMessage());
//        } finally {
//            // 释放链接
//            postMethod.releaseConnection();
//            httpClient.getHttpConnectionManager().closeIdleConnections(0);
//        }
//        return ResultUtil;
//    }
//
//}
