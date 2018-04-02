package com.scd.joggle.constant;

public class Type {
	/**
	 * 轮播类型
	 */
	public final static int SOWING_COMMUNITY = 1;		// 云社区
	public final static int SOWING_ZERO_DISTANCE = 2;	// 零距离
	/**

	 * 云社区大类
	 */
	public final static int COMMODITY_EDUCATION = 1;		// 学习教育
	public final static int COMMODITY_FIND_GOOD = 2;		// 美食分享
	public final static int COMMODITY_HEALTHY_LIFE = 3;	// 生活健康
	public final static int COMMODITY_BODY_BUILDING = 4;	// 健身健美
	public static boolean checkCommodityType(int type) {
		if (	type == COMMODITY_EDUCATION ||
				type == COMMODITY_FIND_GOOD ||
				type == COMMODITY_HEALTHY_LIFE ||
				type == COMMODITY_BODY_BUILDING) {
			return true;
		}
		return false;
	} 
	
	/**
	 * 点赞状态
	 */
	public final static int PRAISE_CANCEL = 0;	// 不点赞状态
	public final static int PRAISE_HIT = 1;	// 点赞状态
	
	/**
	 * 性别  0：无 1：男 2：女
	 */
	public final static int SEX_NONE = 0;
	public final static int SEX_MAIL = 1;
	public final static int SEX_FEMAIL = 2;

	/**
	 * 账号状态  1：可用  2：不可用
	 */
	public final static int ACCOUNT_STATE_USABLE = 1;
	public final static int ACCOUNT_STATE_DISABLE = 2;

	/**
	 * 是否推送消息 1：默认开启  2：禁用
	 */
	public final static int IS_PUSH_MSG_USABLE = 1;
	public final static int IS_PUSH_MSG_DISABLE = 2;

	/**
	 * 支付状态 1：默认可用  2：禁用
	 */
	public final static int PAY_STATE_USABLE = 1;
	public final static int PAY_STATE_DISABLE = 2;

	/**
	 * 会员等级   1：普通会员 2：VIP会员
	 */
	public final static int MEMBER_NORMAL = 1;
	public final static int MEMBER_VIP = 2;

	/**
	 * 订单状态   1：待付款 2：待发货 3：待收货 4：待评价 5：已完成 6：取消订单 7:失效 8：异常单
	 */
	public final static int PENDING_PAYMENT_ORDER = 1;	// 待付款
	public final static int PENDING_SHIPMENT_ORDER = 2;	// 待发货
	public final static int PENDING_RECEIPT_ORDER = 3;	// 待收货
	public final static int TO_BE_EVALUATED_ORDER = 4;	// 待评价
	public final static int COMPLETED_ORDER = 5;		// 完成
	public final static int CANCEL_ORDER = 6;			// 取消
	public final static int FAILURE_ORDER = 7;			// 失效
	public final static int ANOMALY_ORDER = 8;			// 异常
	public static boolean checkOrderState(int type) {
		if (	type == PENDING_PAYMENT_ORDER ||
				type == PENDING_SHIPMENT_ORDER ||
				type == PENDING_RECEIPT_ORDER ||
				type == TO_BE_EVALUATED_ORDER ||
				type == COMPLETED_ORDER ||
				type == CANCEL_ORDER ||
				type == FAILURE_ORDER ||
				type == ANOMALY_ORDER) {
			return true;
		}
		return false;
	}
	
	/**
	 * 商品状态   0：下架 1：上架
	 */
	public final static int COMMODITY_STATE_DOWN = 0;
	public final static int COMMODITY_STATE_UP = 1;

	/**
	 * 收藏状态 1：在收藏  2：已取消
	 */
	public final static int COLLECT_STATE_DOWN = 1;
	public final static int COLLECT_STATE_CANCEL = 2;

	/**
	 * 是否设置支付密码  1:设置 2：没设置
	 */
	public final static int IS_SET_PAY_PASSWORD = 1;
	public final static int NO_SET_PAY_PASSWORD = 2;

	/**
	 * 支付类型  
	 */
	public final static int PAY_ACT_SHOP = 6;
	public final static int PAY_ACT_GRADEUP_VIP = 2;
	public final static int PAY_ACT_RECHARGE = 3;
	public static boolean checkPayAct(int type) {
		if (	type == PAY_ACT_SHOP ||
				type == PAY_ACT_GRADEUP_VIP ||
				type == PAY_ACT_RECHARGE) {
			return true;
		}
		return false;
	}
	
	/**
	 * 支付方式  
	 */
	public static final int PAY_WAY_BALANCE = 1;//余额支付
	public static final int PAY_WAY_SDK = 2;//云付通支付
	public static boolean checkPayState(int type) {
		if (	type == PAY_WAY_BALANCE ||
				type == PAY_WAY_SDK) {
			return true;
		}
		return false;
	}

	/**
	 * 订单支付记录类型  1：单笔  2：多笔合购
	 */
	public final static int PAY_ORDER_TYPE_ONE = 1;
	public final static int PAY_ORDER_TYPE_MORE = 2;


	public static boolean checkCommodityState(int type) {
		if (	type == COMMODITY_STATE_DOWN ||
				type == COMMODITY_STATE_UP) {
			return true;
		}
		return false;
	}
	
	



//	public static final int SHOPPING = 1;//购物
//	public static final int VIP_UP = 2;//会员升级VIP
//	public static final int RECHARGE = 3;//帐户充值

	public static final int NOTICE_FILE = 0;//未接收到通知
	public static final int NOTICC_SUCC = 1;//已接收到通知



	/**
	 * 提醒发货状态
	 */
	public final static int REMIND_NOT_DEAL = 1;	// 提醒发货
	public final static int REMIND_HAS_DEAL = 2;	// 已处理发货
	public static boolean checkRemind(int type) {
		if (	type == REMIND_NOT_DEAL ||
				type == REMIND_HAS_DEAL) {
			return true;
		}
		return false;
	}


	public static final int SHOP_ID = 1;//店铺ID  当前默认为1

	
	/**
	 * 轮播类型
	 * @param type
	 * @return
	 */
	public static boolean isSowing(int type) {
		if (	type == SOWING_COMMUNITY ||
				type == SOWING_ZERO_DISTANCE) {
			return true;
		}
		return false;
	}
	
	/**
	 * 点赞状态
	 * @param type
	 * @return
	 */
	public static boolean isPraise(int type) {
		if (	type == PRAISE_CANCEL ||
				type == PRAISE_HIT) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 默认收货地址  0：不是默认 1：默认
	 */
	public final static int RECEIVE_ADDR_NO = 0;
	public final static int RECEIVE_ADDR_YES = 1;
	public static boolean checkDefaultAddr(int state) {
		if (	state == RECEIVE_ADDR_NO ||
				state == RECEIVE_ADDR_YES) {
			return true;
		}
		return false;
	}
	public static boolean isDefaultAddr(int isDefault) {
		if (isDefault == RECEIVE_ADDR_YES) {
			return true;
		}
		return false;
	}
	
	/**
	 * 商品是否免运费   0：有运费 1：免运费
	 */
	public final static int COMMODITY_EXP_ISFREE_NO = 0;
	public final static int COMMODITY_EXP_ISFREE_YES = 1;
	public static boolean checkCommodityFree(int type) {
		if (	type == COMMODITY_EXP_ISFREE_NO ||
				type == COMMODITY_EXP_ISFREE_YES) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 订单提交类型 1：单订单（立即购买） 2：多订单（购物车）
	 */
	public final static int ORDER_SUBMIT_SINGLE = 1;		// 立即购买
	public final static int ORDER_SUBMIT_MULTI = 2;			// 购物车
	public static boolean checkOrderSubmitType(int type) {
		if (	type == ORDER_SUBMIT_SINGLE ||
				type == ORDER_SUBMIT_MULTI) {
			return true;
		}
		return false;
	}

	/**
	 * 版本 1：末通过 2：已通过
	 */
	public final static int VERSION_NOPASS = 1;
	public final static int VERSION_PASS = 2;
	public static boolean checkVersion(int state) {
		if (	state == VERSION_NOPASS ||
				state == VERSION_PASS) {
			return true;
		}
		return false;
	}

	public final static int APP_TYPE_IOS = 1;
	public final static int APP_TYPE_ANDRIOD = 2;
	public static boolean checkAppType(int type) {
		if (	type == APP_TYPE_IOS ||
				type == APP_TYPE_ANDRIOD) {
			return true;
		}
		return false;
	}
	
	/**
	 * 推送消息状态 1：末读  2：已读
	 */
	public final static int PUSH_READ_NO = 1;
	public final static int PUSH_READ_HAS = 2;
	
	/**
	 * 富文本类型
	 */
	public static final long RICH_TYPE_COMMUNITY = 1;	// 云社区
	public static final long RICH_TYPE_COMMODITY = 2;	// 商品
	public static final long RICH_TYPE_SHOP_ACTIVITY = 3;	// 店铺活动
	
	/**
	 * 合并支付状态
	 * 1：合单支付请求 
	 * 2：已付款
	 */
	public static final int ORDER_MERGE_REQUEST = 1;	// 合单支付请求 
	public static final int ORDER_MERGE_PAY = 2;	// 已付款
	
	
	
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
	public static final int YFT_ASYN_NOTICE_NOPAY = 1;	// 未支付
	public static final int YFT_ASYN_NOTICE_HASPAY = 2;	// 已支付
	public static final int YFT_ASYN_NOTICE_HASDRAWBACK = 3;	// 已退款(全额撤销/冲正)
	public static final int YFT_ASYN_NOTICE_EXPIRED= 4;	// 已过期
	public static final int YFT_ASYN_NOTICE_INVALIDATED = 5;	// 已作废
	public static final int YFT_ASYN_NOTICE_PAYING = 6;	// 支付中
	public static final int YFT_ASYN_NOTICE_DRAWBACKING = 7;	// 退款中
	public static boolean checkYftAsynNoticeState(int type) {
		if (	type == YFT_ASYN_NOTICE_NOPAY ||
				type == YFT_ASYN_NOTICE_HASPAY ||
				type == YFT_ASYN_NOTICE_HASDRAWBACK ||
				type == YFT_ASYN_NOTICE_EXPIRED ||
				type == YFT_ASYN_NOTICE_INVALIDATED ||
				type == YFT_ASYN_NOTICE_PAYING ||
				type == YFT_ASYN_NOTICE_DRAWBACKING) {
			return true;
		}
		return false;
	}
	
	/**
	 * pay sdk 状态
	 * 1:APP请求SDK支付  2:SDK回调处理成功 3：失效
	 */
	public static final int PAY_SDK_REQUEST = 1;	// APP请求SDK支付
	public static final int PAY_SDK_SUCC = 2;	// SDK回调处理成功
	public static final int PAY_SDK_CANCLE = 3;	// 取消付款
	public static boolean checkPaySdkState(int type) {
		if (	type == PAY_SDK_REQUEST ||
				type == PAY_SDK_SUCC ||
				type == PAY_SDK_CANCLE) {
			return true;
		}
		return false;
	}
	
	/**
	 * pay sdk 状态
	 * 1:APP请求SDK支付  2:SDK回调处理成功 3：失效
	 */
	public static final int PAY_NOTICE_PAY = 1;	// 支付通知
	public static final int PAY_NOTICE_ROLL = 2;	// 取消付款
	public static boolean checkPayNoticeState(int type) {
		if (	type == PAY_NOTICE_PAY ||
				type == PAY_NOTICE_ROLL) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 账务类型  1：收入  2：支出
	 */
	public final static int FINANCE_TYPE_INCOME = 1;
	public final static int FINANCE_TYPE_OUTCOME = 2;
	
	/**
	 * 账务科目  1：购物 2：充值 3：推荐人收益
	 */
	public final static int FINANCE_SUBJECT_BUY = 1;
	public final static int FINANCE_SUBJECT_RECHANGE = 2;
	public final static int FINANCE_SUBJECT_EARNINGS = 3;
}
