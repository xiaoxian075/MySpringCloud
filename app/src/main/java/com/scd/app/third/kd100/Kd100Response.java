package com.scd.app.third.kd100;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kd100Response {

	/**
	  * 查询结果状态
	  * 0：物流单暂无结果
	  * 1：查询成功
	  * 2：接口出现异常
	  */
	 private int status;
	 /**
	  *   快递单当前的状态 ：　
	  *   0：在途，即货物处于运输过程中
	  *   1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息
	  *   2：疑难，货物寄送过程出了问题
	  *   3：签收，收件人已签收
	  *   4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收
	  *   5：派件，即快递正在进行同城派件
	  *   6：退回，货物正处于退回发件人的途中
	  */
	 private int state;
	 /**
	  * 返回结果说明
	  */
	 private String message;
	 /**
	  * 返回的data数组
	  */
	 private List<Kd100Node> data;
	 
	 private String nu;
	 
	 private String comcontact;
	 
	 private String condition;
	 private String comurl;
	 private String ischeck;
	 private String com;
}

//"com":"huitongkuaidi",
//"ischeck":"0",
//"comurl":"http://www.800bestex.com/",
//"condition":"00",
//"data":[
//		{
//			"context":"中山市|中山市【中山转运中心】，正发往【厦门转运中心】",
//			"location":"",
//			"time":"2018-03-13 04:21:10"
//		},{
//			"context":"中山市|到中山市【中山转运中心】",
//			"location":"",
//			"time":"2018-03-13 03:33:37"
//		},{
//			"context":"中山市|到中山市【中山横栏镇分部集货点】",
//			"location":"",
//			"time":"2018-03-13 02:56:10"
//		},{
//			"context":"中山市|中山市【中山小榄镇一部001】，【骆勇坚/076087879361】已揽收",
//			"location":"",
//			"time":"2018-03-12 18:28:37"
//		}],
//"comcontact":"95320",
//"nu":"71306607417250",
//"state":"0",
//"message":"ok",
//"status":"1"
