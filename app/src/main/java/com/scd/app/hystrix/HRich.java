package com.scd.app.hystrix;

import org.springframework.stereotype.Component;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FRich;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HRich implements FRich {

	@Override
	public Return<RichBo> selectOne(long type, long foreignId) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}

}
