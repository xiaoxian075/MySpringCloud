package com.scd.async.third.sms.chuanglan;

import com.alibaba.fastjson.JSON;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Beyond
 */
public class ChuangLanSmsUtil {
	
	private final static String CHARSET = "utf-8";
	/**
	 * 连接超时 单位毫秒
	 */
	private final static int CONNECT_TIMEOUT = 10000;
	/**
	 * 读取超时 单位毫秒
	 */
	private final static int READ_TIMEOUT = 10000;
	
	/**
	 * 
	 * @param url		创蓝URL
	 * @param account	创蓝账号
	 * @param pswd		创蓝密码
	 * @param msg		发送内容
	 * @param phone		手机号码（多个手机号用逗号隔开）
	 * @return
	 */
    public static boolean sendMsg(String url, String account, String pswd, String msg, String phone) {
        try {
            //状态报告
            String report = "true";

            SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone, report);

            String requestJson = JSON.toJSONString(smsSingleRequest);
            if (requestJson == null) {
            	return false;
            }

            String response = ChuangLanSmsUtil.sendSmsByPost(url, requestJson);
            if (response == null) {
            	return false;
            }

            SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
            if (smsSingleResponse == null) {
            	return false;
            }
            if (!StringUtils.isEmpty(smsSingleResponse.getMsgId()) && "0".equals(smsSingleResponse.getCode())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    private static String sendSmsByPost(String smsUrl, String postContent) {
    	if (smsUrl == null) {
    		return null;
    	}
        URL url = null;
        try {
            url = new URL(smsUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout(READ_TIMEOUT);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", CHARSET);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(postContent.getBytes(CHARSET));
            os.flush();

            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                // 开始获取数据
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), CHARSET));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static String listToStr(List<String> sList, String symbol) {
//        String str = "";
//        if (sList != null && sList.size() > 0) {
//            for (int i = 0; i < sList.size(); i++) {
//                str += sList.get(i) + symbol;
//            }
//            if (str.endsWith(symbol)) {
//                str = str.substring(0, str.length() - 1);
//            }
//        }
//        return str;
//    }
}
