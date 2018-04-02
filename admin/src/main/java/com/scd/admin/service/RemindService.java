package com.scd.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FRemind;
import com.scd.admin.pojo.util.RemindUtil;
import com.scd.admin.pojo.vo.RemindVo;
import com.scd.joggle.pojo.po.RemindPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class RemindService {
	
	@Autowired
	private FRemind fRemind;

	public Return<PageInfo<RemindVo>> list(int page, int size, int state) {
		Return<PageInfo<RemindPo>> ret = fRemind.getList(page, size, state);
		if (Return.isErr(ret)) {
			return Constant.createReturn(ret.getCode(), ret.getDesc());
		}
		
		PageInfo<RemindPo> pageInfo = ret.getData();
        List<RemindPo> poList = pageInfo.getList();
        List<RemindVo> voList = RemindUtil.change(poList);

        PageInfo<RemindVo> kdPage = new PageInfo<RemindVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);

        return Constant.createReturn(kdPage);
	}

	public Return<Object> ignore(long id) {
		return fRemind.ignore(id);
	}

}
