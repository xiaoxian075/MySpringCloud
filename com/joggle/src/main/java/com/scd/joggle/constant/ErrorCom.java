package com.scd.joggle.constant;

public enum ErrorCom {
	SUCCESS(0, "succ"),
	GET_SESSION(1, "请先获取Session"),
	GET_LOGIN(2, "请登入"),
	GET_LOGIN_NOT_MATCH_SEEESION(3, "session跟账号不匹配"),

	ERROR(8001, "异常"),
	FEIGN_ERROR(8002, "feign调用失败"),
	FEIGN_RETURN_ERROR(8003, "feign返回失败"),
	FILE_UPLOAD_ERROR(8004, "文件上传失败"),
	UNPACK_ERROR(8005, "解包错误"),
	PARSE_ERROR(8006, "参数错误"),
	SQL_EXE_ERROR(8007, "数据库执行出错"),
	CHANGE_ERROR(8008, "类型转换异常"),
	REPEATED_SUBMIT(8009, "重复提交"),
	SAVE_FAIL(8010, "保存失败"),
	NOT_ORDER_INFO(8010, "订单不存在"),
	HEAD_UPLOAD_ERROR(8011, "头像上传失败"),
	PUSH_ERROR(8012, "推送失败"),

	
	
	PHONE_NUMBER_ERROR(2001, "手机号有误"),
	GET_CHECK_CODE_ERROR(2002, "验证码有误"),
	FIRST_GET_CODE(2003, "请先获取验证码"),
	USE_CORRECT_PHONE(2004, "请使用获取验证码的手机号"),
	CODE_ERROR(2005, "验证码有误"),
	CODE_EXCEED(2006, "验证码已过期，请重新获取"),
	PASSWORD_ERROR(2007, "密码为数字、字母和字符任意两种组合，长度为8-20位"),
	REFEREE_ERROR(2008, "推荐人有误"),
	PHONE_HAS_REGISTER(2009, "手机号已注册"),
	SEND_CODE_ERROR(2010, "发送验证码失败"),
	LOGIN_NAME_ERROR(2011, "登入名为字母，数字，中文 2~10个字符"),
	LOGIN_NAME_NOT_EXIST(2012, "账号不存在"),
	PASSWORD_NOT_MATCH(2013, "密码不对"),
	REGISTER_FAIL(2014, "注册失败"),
	LOGINNAME_OR_PASSWORD_ERROR(2015, "登入名或密码不对"),
	SYN_ACCOUNT_FAIL(2016, "同步账号失败"),
	LOGIN_NAME_HAS_EXIST(2017, "账号已存在"),
	GET_CODE_FREQUENTLY(2018, "不要过于频繁获取验证码"),
	NOT_REFEREE_POWER(2019, "没有推荐权限"),
	SET_PAY_PASSWORD_ERROR(2020, "设置支付密码失败"),
	PASSWORD_ERROR_MATCH(2021, "输入的旧密码不正确"),
	UPDATE_PASSWORD_ERROR(2022, "修改登录密码失败"),
	TWO_PASSWORD_NOT_EQUAL(2023, "两次密码不一致"),

	NOT_EXIST(3001, "对象不存在"),
	HAS_EXIST(3002, "对象已存在"),
	THIRD_ERROR(3000, "第三方调用错误"),
	NO_POWER(3003, "权限不对"),
	STATE_FAIL(3004, "状态不对"),
	NOT_COLLECT(3005, "该商品还未收藏"),

	
	YUN_PAY_LOGIN_SUCCESS(4001,"登录成功"),
	YUN_PAY_LOGIN_ERROR(4002,"登录失败，联系客服"),

	NO_BALANCE_ENOUGH(5001,"当前账号余额不足"),
	SET_PAY_PASSWORD(5002,"先请设置支付密码"),
	SET_PAY_STATE(5003,"账号不可支付,请联系管理员"),
	ERR_PAY_PASSWORD(5004,"支付密码不正确"),
	ERR_PAY_VIP(5005,"当前账号等级已是VIP"),
	ERR_PAY(5006,"支付失败，请联系客服"),
	ERR_PAY_ALERY(5007,"订单已经支付"),

	COM_NO_UPDATE(9001, "没有更新"),
	CAROUSEL_NOT_EXIST(9002, "轮播图记录不存在"),
	COMMUNITY_NOT_EXIST(9003, "云社区资讯记录不存在"),
	//NOT_EXIST(9004, "对象不存在"),
	AREA_NOT_EXIST(9005, "地区不存在"),
	ORDER_PARAM_ERROR(9006, "商品参数错误"),
	SHOP_NOT_EXIST(9007, "店铺不存在"),
	COMMODITY_NOT_EXIST(9008, "商品不存在"),
	ADDRESS_NOT_EXIST(9009, "收获地址不存在"),
	GET_EXP_PRICE_ERROR(9010, "计算运费出错"),
	SHOP_NAME_EXIST(9011, "店铺名称已存在"),
    SHOP_IS_USE(9019, "店铺已被选择使用，不可删除"),
	ADDRESS_NO_DEFAULT(9012, "没有默认地址"),
	COMMODITY_ATTR_HAS_EXIST(9013, "商品属性值重复"),
	COMMODITY_ATTR_NOT_EXIST(9014, "商品属性不存在"),
	SAVE_ERROR(9015, "保存失败"),
	UPDATE_ERROR(9016, "更新失败"),
	ORDER_NOT_EXIST(9017, "订单不存在"),
	ORDER_STATE_ERROR(9018, "订单状态错误"),
	ALREADY_COLLECT(9019, "该商品已收藏过"),
	ACCOUNT_NOT_EXIST(9020, "账号不存在"),
	ZHIBEI_NOT_ERROR(9021, "智贝数据错误"),
	COMMODITY_NOT_HAS_ATTR(9022, "商品没添加属性"),
	COMMODITY_ATTR_SOCK_NOT_ENOUGN(9023, "商品库存不足"),
	DELETE_FAIL(9024, "删除失败"),
	YFT_NOTICE_STATE(9025, "未支付"),
	ACCOUNT_NOT_PAY(9026, "此账号不可支付"),
	ACCOUNT_BALANCE_NOT_ENOUGN(9027, "余额不足"),
	CONFIG_ERROR(9028, "配置有误"),
	CREATE_ODD_ERROR(9029, "无法产生订单号"),
	PAY_RESULT_NOT_EXIST(9030, "没有此支付结果"),
	


	/********* 第三方调用返回段 10001~10099 *********/
	KD100_GET_ERROR(10001, "快递100查询失败"),
	KD100_NO_RESULT(10002, "物流单暂无结果"),
	
	KD100_NAME_EXIST(10011, "快递公司名称已存在"),
	KD100_CODE_EXIST(10012, "快递公司简码已存在"),
	KD100_NOT_EXIST(10013, "快递公司不存在"),

    EXPENSES_NOT_EXIST(10053, "运费地区不存在"),
	
	YFT_ERROR(100021, "云付通调用失败");
	
	

	

	

	
	
	
	
	
	private int code;
	private String desc;
	private ErrorCom(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
