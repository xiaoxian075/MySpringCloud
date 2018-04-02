package com.scd.joggle.pojo.bo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YftAsynNoticeBo {

	/**
	 * 云付通交易流水号  String(32)
	 */
    private String tradeNo;
    
    
    /**
     * 通知类型
     * 1: 支付通知 
     * 2： 取消付款通知
     */
    private int noticeType;
    
    /**
     * 支付状态 
	 * 01未支付
	 * 02已支付
	 * 03已退款(全额撤销/冲正)
	 * 04已过期
	 * 05已作废
	 * 06支付中
	 * 07退款中
     */
    private int state;
    
    /**
     * 支付账户
     * 云付通实际支付帐号
     */
    private String payYunId;
    
    /**
     * 商户订单号  String(32)
     */
    private String orderId;
    
    /**
     * 支付金额
     * 保留2位小数点
     */
    private BigDecimal realAmount;
    
    /**
     * 订单交易时间   String(14)
     * 交易时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
     */
    private String tradeTime;
    

}
