package com.scd.coredb.third;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scd.coredb.pojo.db.TPaySdk;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.YftAsynNoticeBo;
import com.scd.joggle.pojo.po.PayInfoPo;
import com.scd.sdk.util.MD5Util;
import com.scd.sdk.util.TimeUtil;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ThirdYft {
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	private ThirdYft() {
	}
	private static class ThirdYftFactory {
		private static ThirdYft instance = new ThirdYft();
	}
	public static ThirdYft getInstance() {
		return ThirdYftFactory.instance;
	}
	
	private String yftSingKey;
    private String yftYzjClientId;
    private String yftYzjReceiveAccount;
    private long yftYzjPayTimeout;
    
    private final static BigDecimal profit = new BigDecimal("0.01");
    private final static BigDecimal TOTAL_CONSUME = new BigDecimal("5000");
    private final static BigDecimal TOTAL_RECHARGE = new BigDecimal("3000");
    
    /**
     * 计算推荐人收益
     * @param amount
     * @return
     */
    public BigDecimal calcEarnings(BigDecimal amount) {
    	if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
    		return null;
    	}
    	
    	BigDecimal earnings = amount.multiply(profit);
    	// 四舍五入
    	earnings = earnings.setScale(0, BigDecimal.ROUND_HALF_UP);
    	
    	return earnings;
    }
    
    /**
     * 计算积分
     * @param amount
     * @return
     */
    public long caclIntegral(BigDecimal amount) {
    	if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
    		return 0;
    	}

    	// 四舍五入
    	BigDecimal integral = amount.setScale(0, BigDecimal.ROUND_HALF_UP);
    	
    	return integral.longValue();
    }
	
	public void init(String yftSingKey, String yftYzjClientId, String yftYzjReceiveAccount, long yftYzjPayTimeout) {
		this.yftSingKey = yftSingKey;
		this.yftYzjClientId = yftYzjClientId;
		this.yftYzjReceiveAccount = yftYzjReceiveAccount;
		this.yftYzjPayTimeout = yftYzjPayTimeout;
	}

	public YftAsynNoticeBo paramAsynNotice(String data) {
		try {
		JSONObject object = JSON.parseObject(data);
        if (object == null) {
        	return null;
        }
        String tradeNo = object.getString("tradeNo");
        String orderId = object.getString("orderId");
        String realAmount = object.getString("realAmount");
        String tradeTime = object.getString("tradeTime");
        String payYunId = object.getString("payYunId");
        String noticeType = object.getString("noticeType");
        String statusCode = object.getString("statusCode");
        String sign = object.getString("sign");
        
        if (	tradeNo == null || tradeNo.length() == 0 ||
        		orderId == null || orderId.length() == 0 ||
        		realAmount == null || realAmount.length() == 0 ||
        		tradeTime == null || tradeTime.length() == 0 ||
        		payYunId == null || payYunId.length() == 0 ||
        		noticeType == null || noticeType.length() == 0 ||
        		statusCode == null || statusCode.length() == 0 ||
        		sign == null || sign.length() == 0) {
        	return null;
        }


        StringBuilder builder = new StringBuilder();
        builder.append(noticeType).append("&")
                .append(orderId).append("&")
                .append(payYunId).append("&")
                .append(realAmount).append("&")
                .append(statusCode).append("&")
                .append(tradeNo).append("&")
                .append(tradeTime).append("&")
                .append(yftSingKey);
        String hostSign = MD5Util.encodeByMD5(builder.toString());
        if (hostSign == null || hostSign.length() == 0) {
        	return null;
        }
        
        sign = sign.toUpperCase();
        if(!hostSign.equals(sign)) {
            return null;
        }
        
        BigDecimal bRealAmount = new BigDecimal(realAmount);
        int iNoticeType = 0;
        if ("payNotice".equals(noticeType)) {
        	iNoticeType = 1;
        } else if ("cancelPayNotice".equals(noticeType)) {
        	iNoticeType = 2;
        } else {
        	return null;
        }

        int iStatusCode = Integer.parseInt(statusCode);
        if (!Type.checkYftAsynNoticeState(iStatusCode)) {
        	return null;
        }
        
        return new YftAsynNoticeBo(
        		tradeNo,
        		iNoticeType,
        		iStatusCode,
        		payYunId,
        		orderId,
        		bRealAmount,
        		tradeTime
        		);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 添加配置信息及验签
	 * @param po
	 * @return
	 */
	public PayInfoPo createPayInfo(TPaySdk tPaySdk) {
		if (tPaySdk == null) {
			return null;
		}

		String clientId = tPaySdk.getClientId();
		String odd = tPaySdk.getOdd();
		String act = String.valueOf(tPaySdk.getAct());
		String amount = tPaySdk.getAmount().toString();
		String payAccount = tPaySdk.getPayAccount();
		String receiveAccount = tPaySdk.getReceiveAccount();
		String extData = "";
		String orderDesc = "";
		long lTradeTime = tPaySdk.getTradeTime();
		long lTimeoutTime = tPaySdk.getLastPayTime();
		String tradeTime = TimeUtil.changeToStr(lTradeTime, "yyyyMMddHHmmss");
		String timeoutTime = TimeUtil.changeToStr(lTimeoutTime, "yyyyMMddHHmmss");
		if (tradeTime == null || timeoutTime == null) {
			return null;
		}
		
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(act).append("&")
                .append(amount).append("&")
                .append(clientId).append("&")
                .append(extData).append("&")
                .append(payAccount).append("&")
                .append(receiveAccount).append("&")
                .append(orderDesc).append("&")
                .append(odd).append("&")
                .append(timeoutTime).append("&")
                .append(tradeTime).append("&")
                .append(yftSingKey);
        String sign = MD5Util.encodeByMD5Base64(stringBuilder.toString());
        if (sign == null || sign.length() == 0) {
        	return null;
        }
        
		return new PayInfoPo(
				"v1.0.0",
				clientId,
				receiveAccount,
				odd,
				payAccount,
				amount,
				tradeTime,
				timeoutTime,
				orderDesc,
				extData,
				act,
				sign
				);
	}

	public boolean checkVip(BigDecimal totalConsume, BigDecimal totalRecharge) {
		if (totalConsume == null || totalConsume.compareTo(BigDecimal.ZERO) < 0 ||
				totalRecharge == null || totalRecharge.compareTo(BigDecimal.ZERO) < 0 ) {
			return false;
		}
		
		if (TOTAL_CONSUME.compareTo(totalConsume) <= 0 ||
				TOTAL_RECHARGE.compareTo(totalRecharge) <= 0) {
			return true;
		}
		return false;
	}
}