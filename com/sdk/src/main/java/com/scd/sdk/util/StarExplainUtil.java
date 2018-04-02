package com.scd.sdk.util;

import com.scd.sdk.common.StarExplain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018-03-06.
 */
public class StarExplainUtil {

    private static final Logger logger = LoggerFactory.getLogger(StarExplainUtil.class);

    public static String getExplain(int type){
        String name = "";
        switch (type){
            case 1: name = StarExplain.STAR_ONE.getDesc();break;
            case 2: name = StarExplain.STAR_TWO.getDesc();break;
            case 3: name = StarExplain.STAR_THERR.getDesc();break;
            case 4: name = StarExplain.STAR_FOUR.getDesc();break;
            case 5: name = StarExplain.STAR_FIVE.getDesc();break;
        }
        return name;
    }

    public static String getClientPostStr(HttpServletRequest request, String charsetName) {
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    request.getInputStream(), charsetName));
            char tagChar[];
            tagChar = new char[1024];
            int len;
            String temp = "";
            while ( (len = reader.read(tagChar)) != -1) {
                temp = new String(tagChar, 0, len);
                result += temp;
                temp = null;
            }
            reader.close();
        }
        catch (Exception ex) {
            logger.error("获取云支付回调返回参数从request解析异常：", ex);
        }
        finally {

        }
        return result;
    }
}
