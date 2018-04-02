package com.scd.app.third;

import com.scd.app.constant.Constant;
import com.scd.app.third.kd100.Kd100Response;
import com.scd.app.third.kd100.Kd100Vo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.GsonUtil;
import com.scd.sdk.util.HttpUtil;
import com.scd.sdk.util.pojo.Return;


public class ThirdKd {

    private ThirdKd() {
    }
    private static class ThirdKdFactory {
        private static ThirdKd instance = new ThirdKd();
    }
    public static ThirdKd getInstance() {
        return ThirdKdFactory.instance;
    }
    
    /**
     * 快递100请求URL
     */
    private String kd100Url;
    /**
     * 快递100 KEY
     */
    private String kd100Key;
    
    
    
    public void init(String kd100Url, String kd100Key) {
    	this.kd100Url = kd100Url;
    	this.kd100Key = kd100Key;
    }
  
    public Return<Kd100Vo> queryOne(String code, String orderNo) {
    	Kd100Response response = queryExpress(kd100Url, kd100Key, code, orderNo);
		if (response == null) {
			return Constant.createReturn(ErrorCom.KD100_GET_ERROR);
		}
		
		/**
	     * 0：物流单暂无结果
	     * 1：查询成功
	     * 2：接口出现异常
		 */
		int status = response.getStatus();
		if (status == 0) {
			return Constant.createReturn(ErrorCom.KD100_NO_RESULT);
		} else if (status == 1) {
			Kd100Vo vo = new Kd100Vo(
					response.getState(),
					response.getNu(),
					response.getComcontact(),
					Integer.parseInt(response.getIscheck()),
					response.getCom(),
					"",
					response.getData()
					);
			return Constant.createReturn(vo);
		} else {
			return Constant.createReturn(ErrorCom.KD100_GET_ERROR);
		}
    }
    
	private static Kd100Response queryExpress(String url, String key, String code, String orderNo) {

		String _url = url + "?id=" + key + "&com=" + code + "&nu=" + orderNo + "&show=0&muti=1&order=desc";
    	String data = HttpUtil.httpGet(_url);
    	if (data == null || data.length() == 0) {
    		return null;
    	}

    	return GsonUtil.toJson(data, Kd100Response.class);
	}
}


