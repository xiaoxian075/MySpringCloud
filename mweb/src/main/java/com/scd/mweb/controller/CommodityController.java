package com.scd.mweb.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.joggle.constant.ErrorCom;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.controller.accept.CommodityPageAcceptMsg;
import com.scd.mweb.pojo.util.CommodityUtil;
import com.scd.mweb.pojo.vo.CommodityAssortVo;
import com.scd.mweb.pojo.vo.CommodityAttrShowVo;
import com.scd.mweb.pojo.vo.CommodityDetailVo;
import com.scd.mweb.pojo.vo.CommodityRecommendVo;
import com.scd.mweb.pojo.vo.CommodityShortVo;
import com.scd.mweb.pojo.vo.CommodityVo;
import com.scd.mweb.service.CommodityService;
import com.scd.sdk.util.pojo.H5IdAcceptMsg;
import com.scd.sdk.util.pojo.Return;

/**
 * @ClassName CommodityController
 * @Description 商品
 * @author chenjx
 * @date 2018-03-05
 */

@RestController
@RequestMapping(value = "/commodity")
public class CommodityController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private CommodityService commodityService;
	
	
	
	/**
	 * 获取商品列表
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public String getList(HttpServletRequest request) {
		List<CommodityShortVo> voList = null;
		try {
			CommodityPageAcceptMsg acceptMsg = Constant.subPack(request, CommodityPageAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			long shopId = acceptMsg.getShopId();
			int type = acceptMsg.getType();
			int page = acceptMsg.getPage();
			int size = acceptMsg.getSize();
			Return<List<CommodityVo>> ret = commodityService.getList(shopId, type, page, size);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			voList = CommodityUtil.changeShort(ret.getData());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(voList);
	}
	
	/**
	 * 获取商品详细信息
	 * @return
	 */
	@RequestMapping(value = "/getDetail", method = RequestMethod.POST)
	public String getDetail(HttpServletRequest request) {
		CommodityDetailVo detailVo = null;
		try {
			H5IdAcceptMsg acceptMsg = Constant.subPack(request, H5IdAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (acceptMsg.getId() <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			long id = acceptMsg.getId();
			Return<CommodityVo> ret = commodityService.getOne(id);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			detailVo = CommodityUtil.changeDetail(ret.getData());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(detailVo);
	}
	
	/**
	 * 获取商品属性信息
	 * @return
	 */
	@RequestMapping(value = "/getAttr", method = RequestMethod.POST)
	public String getAttr(HttpServletRequest request) {
		CommodityAttrShowVo attrVo = null;
		try {
			H5IdAcceptMsg acceptMsg = Constant.subPack(request, H5IdAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (acceptMsg.getId() <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			long id = acceptMsg.getId();
			Return<CommodityVo> ret = commodityService.getOne(id);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			attrVo = CommodityUtil.changeAttr(ret.getData());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(attrVo);
	}
	
	/**
	 * 为你搭配
	 * @return
	 */
	@RequestMapping(value = "/getAssort", method = RequestMethod.POST)
	public String getAssort(HttpServletRequest request) {
		List<CommodityAssortVo> voList = null;
		try {
			H5IdAcceptMsg acceptMsg = Constant.subPack(request, H5IdAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (acceptMsg.getId() <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			long id = acceptMsg.getId();
			Return<List<CommodityAssortVo>> ret = commodityService.getAssort(id);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			voList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(voList);
	}
	
	/**
	 * 为你推荐
	 * @return
	 */
	@RequestMapping(value = "/getRecommend", method = RequestMethod.POST)
	public String getRecommend(HttpServletRequest request) {
		List<CommodityRecommendVo> voList = null;
		try {
			H5IdAcceptMsg acceptMsg = Constant.subPack(request, H5IdAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (acceptMsg.getId() <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			long id = acceptMsg.getId();
			Return<List<CommodityRecommendVo>> ret = commodityService.getRecommend(id);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			voList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(voList);
	}
}
