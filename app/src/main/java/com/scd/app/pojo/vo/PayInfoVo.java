package com.scd.app.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayInfoVo {
    private String versionName;		// 版本号 v1.0.0
    private String clientId;		// 云付通分配给应用的
    private String merchantLoginName;	// 商户收款账号  目前云智居为 “05”
    private String orderId;				// 订单编号
    private String loginName;			// 付款账号
    private String amount;				// 金额
    private String tradeTime;			// 交易时间  YYYYMMDDHHMMSS 20180327112405 
    private String timeoutTime;			// 失效时间  YYYYMMDDHHMMSS 20180327112405 
    private String orderDesc;			// 备注
    private String extData;				// 预留字段
    private String act;					// 动作 6：购物 3：充值 2：升级
    private String sign;				// 验签
}
