package com.scd.sdk.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class RandomUtil {

	private static Random rand = new Random();
	
	public static int randInt(int min, int max) {
		if(max < min) {
			return 0;
		}
		
		int bound = max - min + 1;
		
		return rand.nextInt(bound) + min;
	}
	
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String getOdd() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
    /**************************************************************
     * 方 法 名：randNumID
     * 名     称： 随机ID
     * ver :1.0
     * 参数说明：
     * <p/>
     * 返 回 值：
     * retStr
     * 作用/用途：
     * 唯一性关键字
     ****************************************************/
    public static final String randNumID() {
        String retStr = "";
        String rand = "";
        //产生随机六位数字
        for (int i = 0; i < 6; i++) {
            int j = (int) (Math.random() * (9 - i)) + i;
            rand = rand + String.valueOf(j);
        }
        //时间算到毫秒
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String regdate;
        regdate = formatter.format(new Date());
        retStr = regdate + rand;
        return retStr;
    }
    
    public static String getYftOdd(String head) {
    	if (head == null || head.length() == 0 || head.length() > 9) {
    		return null;
    	}
    	String odd = head;
    	try {
    		String body = randNumID();
    		if (body == null || body.length() != 23) {
    			return null;
    		}
    		odd += body;
    	} catch (Exception e) {
    		return null;
    	}
    	
    	return odd;
    }
}
