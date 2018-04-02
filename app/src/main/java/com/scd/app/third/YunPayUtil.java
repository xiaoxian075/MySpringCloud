//package com.scd.app.third;
//
//import com.scd.app.config.CommonConfig;
//import com.scd.joggle.constant.Type;
//import com.scd.sdk.util.DesUtil;
//import com.scd.sdk.util.FunUtil;
//import com.scd.sdk.util.MD5Util;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Administrator on 2018-03-07.
// */
//public class YunPayUtil {
//	private final static Logger LOGGER = LoggerFactory.getLogger(YunPayUtil.class);
//    public static String YZJ_ACCOUNT ="05";
//    public static String CLIENT_ID ="10931";
//    private static String SING_KEY = "TK6SU81PCFcdH50i728zd2E1Xvs18nuf";
//
//    /**
//     * 组合云付通APK支付数据
//     */
//    public static Map getYunPayApkPayData(String account, int payAct, BigDecimal amount,String odd){
//        Map map = null;
//        try{
//            String act = "";
//            String orderDesc = "";
//            if(Type.SHOPPING == payAct){
//                act = "6";
//                orderDesc = "云智居购物订单备注";
//            }else if(Type.RECHARGE == payAct){
//                act = "3";
//                orderDesc = "云智居充值订单备注";
//            }else if(Type.VIP_UP == payAct){
//                act = "2";
//                orderDesc = "云智居升级订单备注";
//            }
//            map = new HashMap<>();
//            //订单过期时间  15分钟
//            String timeoutTime = FunUtil.getFormatDate(FunUtil.dateAddSecond(new Date(),900),"yyyyMMddHHmmss");
//            String tradeTime = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
//            String extData = "0";
//            map.put("act",act);//动作
//            map.put("amount",amount);//支付金额
//            map.put("clientId",CLIENT_ID);//授权商户ID
//            map.put("extData",extData);//扩展字段
//            map.put("loginName",account);//支付帐号
//            map.put("merchantLoginName",YZJ_ACCOUNT);//收款帐号
//            map.put("orderDesc",orderDesc);//备注
//            map.put("orderId",odd);//订单号
//            map.put("timeoutTime",timeoutTime);//订单超时时间
//            map.put("tradeTime",tradeTime);//交易时间
//            LOGGER.info(FunUtil.mapToString(map));
//            map.put("putMoney",amount);//保留字段
//            map.put("putOrderId",odd);//保留字段
//
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(act).append("&")
//                    .append(amount).append("&")
//                    .append(CLIENT_ID).append("&")
//                    .append(extData).append("&")
//                    .append(account).append("&")
//                    .append(YZJ_ACCOUNT).append("&")
//                    .append(orderDesc).append("&")
//                    .append(odd).append("&")
//                    .append(timeoutTime).append("&")
//                    .append(tradeTime).append("&").append(CommonConfig.SKD_SING_KEY);
//
//            String sign = MD5Util.encodeByMD5Base64(stringBuilder.toString());
//            map.put("sign",sign);
//            LOGGER.info("sign原值:".concat(stringBuilder.toString()).concat("sign验签后:".concat(sign)));
//        }catch (Exception e){
//            LOGGER.error("e",e);
//        }
//        return map;
//    }
//
//
//    /**
//     * 获取 Map 数据
//     * @param data
//     * @return
//     */
//    public static String getDataPostMap(String data, String appPostDesKey) {
//        String req = "";
//        try {
//            //解密
//            req = DesUtil.decrypt(data, appPostDesKey);
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        finally {
//        }
//        return req;
//    }
//
//}
