package com.scd.sdk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * 常用获取客户端信息的工具
 */
public final class NetworkUtil {
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public final static String getIpAddress(HttpServletRequest request) throws IOException {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
	
    public static String readFromRequest(HttpServletRequest request, String charsetName) {
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    request.getInputStream(), charsetName));
            char tagChar[] = new char[1024];
            int len;
            while ( (len = reader.read(tagChar)) != -1) {
                result += new String(tagChar, 0, len);
            }
            reader.close();
        }
        catch (Exception ex) {
            return null;
        }
        return result;
    }
	
//	public static String createSessionId() {
//		String uuid = UUID.randomUUID().toString();
//		uuid = uuid.replace("-", "");
//		return uuid;
//	}
}