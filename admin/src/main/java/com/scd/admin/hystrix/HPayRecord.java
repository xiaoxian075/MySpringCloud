package com.scd.admin.hystrix;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FPayRecord;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.PayRecordSelectParam;
import com.scd.joggle.pojo.po.PayRecordPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.stereotype.Component;

@Component
public class HPayRecord implements FPayRecord {
	@Override
	public Return<PageInfo<PayRecordPo>> list(PayRecordSelectParam param) {
		return Constant.createReturn(ErrorCom.FEIGN_ERROR);
	}


}
