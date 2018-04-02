package com.scd.mweb.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.scd.joggle.constant.ErrorCom;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.controller.accept.GetCommunityListAcceptMsg;
import com.scd.mweb.pojo.vo.CommunityVo;
import com.scd.mweb.service.CommunityService;
import com.scd.sdk.util.pojo.Return;

/**
 * @ClassName CommunityController
 * @Description 云社区
 * @author chenjx
 * @date 2018-02-06
 */

@RestController
@RequestMapping(value = "/ct")
public class CommunityController {
	
	@Resource
	private CommunityService communityService;
	
	/**
	 * 获取列表
	 * @param 	type	类型 1：学习教育 2：美食分享 3：生活健康 4：健身健美
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public String getList(HttpServletRequest request) {
		GetCommunityListAcceptMsg acceptMsg = Constant.subPack(request, GetCommunityListAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		// 根据类型获取轮播图
		int page = acceptMsg.getPage();
		int size = acceptMsg.getSize();
		int type = acceptMsg.getType();
		Return<List<CommunityVo>> ret = communityService.getList("", type, page, size);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
	
}
