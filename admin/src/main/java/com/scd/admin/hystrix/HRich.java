package com.scd.admin.hystrix;

import org.springframework.stereotype.Component;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FRich;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.sdk.util.pojo.Return;

@Component
public class HRich implements FRich {


	@Override
	public Return<RichBo> selectOne(long type, long foreignId) {
		return Constant.createReturn(ErrorCom.ERROR);
	}
	
	@Override
	public Return<RichBo> submit(RichBo richBo) {
		return Constant.createReturn(ErrorCom.ERROR);
	}

}
