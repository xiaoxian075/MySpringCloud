package com.scd.async.third;

import com.scd.async.constant.Constant;
import com.scd.async.third.express.kd100.Kd100Response;
import com.scd.async.third.express.kd100.Kd100Util;
import com.scd.async.third.express.kd100.Kd100Vo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.Return;


public class ThirdKd {
	//private static Logger logger = LoggerFactory.getLogger(ThirdChit.class);

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
    	Kd100Response response = Kd100Util.queryExpress(kd100Url, kd100Key, code, orderNo);
		if (response == null) {
			return Constant.createReturn(ErrorCom.KD100_GET_ERROR);
		}
		
//	     * 0：物流单暂无结果
//	     * 1：查询成功
//	     * 2：接口出现异常
		int status = response.getStatus();
		if (status == 0) {
			return Constant.createReturn(ErrorCom.KD100_NO_RESULT);
		} else if (status == 1) {
			return Constant.createReturn(new Kd100Vo(response.getState(), response.getMessage(), response.getKuaidiDataList()));
		} else {
			return Constant.createReturn(ErrorCom.KD100_GET_ERROR);
		}
    }
}


