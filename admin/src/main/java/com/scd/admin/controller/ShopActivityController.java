package com.scd.admin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.ShopActivityAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.ShopActivityVo;
import com.scd.admin.service.ShopActivityService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.IdPageAcceptMsg;
import com.scd.sdk.util.pojo.IdlistAcceptMsg;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/shopActivity")
public class ShopActivityController {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ShopActivityService shopActivityService;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletRequest request) {
		PageInfo<ShopActivityVo> pageInfo = null;
		 try {
	        IdPageAcceptMsg acceptMsg = Constant.subPack(request, IdPageAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}

		      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		      if (session == null) {
		          return Constant.pack(ErrorCom.GET_LOGIN);
		      }

	        int page = acceptMsg.getPage();
	        int size = acceptMsg.getSize();
	        long shopId = acceptMsg.getId();
	        Return<PageInfo<ShopActivityVo>> ret = shopActivityService.list(page, size, shopId);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }
	        
	        pageInfo = ret.getData();
		 } catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		 }

	        return Constant.pack(pageInfo);
	    }

	    @RequestMapping(value = "/add", method = RequestMethod.POST)
	    public String add(HttpServletRequest request) {
	    	ShopActivityVo vo = null;
	    	try {
		        ShopActivityAcceptMsg acceptMsg = Constant.subPack(request, ShopActivityAcceptMsg.class);
				if (acceptMsg == null) {
					return Constant.pack(ErrorCom.UNPACK_ERROR);
				}
				if (!acceptMsg.check()) {
					return Constant.pack(ErrorCom.PARSE_ERROR);
				}
	
			      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			      if (session == null) {
			          return Constant.pack(ErrorCom.GET_LOGIN);
			      }
	
		        long shopId = acceptMsg.getShopId();
		        String title = acceptMsg.getTitle();
		        Return<ShopActivityVo> ret = shopActivityService.add(shopId, title);
		        if (Return.isErr(ret)) {
		            return Constant.pack(ret);
		        }
		        
		        vo = ret.getData();
			 } catch (Exception e) {
				logger.error(e.getMessage());
				return Constant.pack(ErrorCom.ERROR);
			 }	        

	        return Constant.pack(vo);
	    }

	    @RequestMapping(value = "/edit", method = RequestMethod.POST)
	    public String edit(HttpServletRequest request) {
	    	ShopActivityVo vo = null;
	    	try {
	    		ShopActivityAcceptMsg acceptMsg = Constant.subPack(request, ShopActivityAcceptMsg.class);
				if (acceptMsg == null) {
					return Constant.pack(ErrorCom.UNPACK_ERROR);
				}
				if (!acceptMsg.check()) {
					return Constant.pack(ErrorCom.PARSE_ERROR);
				}
	
			      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			      if (session == null) {
			          return Constant.pack(ErrorCom.GET_LOGIN);
			      }
	
		        long id = acceptMsg.getId();
		        long shopId = acceptMsg.getShopId();
		        String title = acceptMsg.getTitle();
		        Return<ShopActivityVo> ret = shopActivityService.edit(id, shopId, title);
		        if (Return.isErr(ret)) {
		            return Constant.pack(ret);
		        }
		        
		        vo = ret.getData();
			 } catch (Exception e) {
				logger.error(e.getMessage());
				return Constant.pack(ErrorCom.ERROR);
			 }	
	        return Constant.pack(vo);
	    }

	    @RequestMapping(value = "/del", method = RequestMethod.POST)
	    public String del(HttpServletRequest request) {
	    	long returnId = 0;
	    	try {
		        IdAcceptMsg acceptMsg = Constant.subPack(request, IdAcceptMsg.class);
				if (acceptMsg == null) {
					return Constant.pack(ErrorCom.UNPACK_ERROR);
				}
				if (!acceptMsg.check()) {
					return Constant.pack(ErrorCom.PARSE_ERROR);
				}
	
			      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			      if (session == null) {
			          return Constant.pack(ErrorCom.GET_LOGIN);
			      }
				
		        long id = acceptMsg.getId();
		        Return<Long> ret = shopActivityService.del(id);
		        if (Return.isErr(ret)) {
		            return Constant.pack(ret);
		        }
		        
		        returnId = ret.getData();
			 } catch (Exception e) {
				logger.error(e.getMessage());
				return Constant.pack(ErrorCom.ERROR);
			 }			        

	        return Constant.pack(returnId);
	    }

	    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
	    public String batchDel(HttpServletRequest request) {
	    	List<Long> returnList = null;
	    	try {
		        IdlistAcceptMsg acceptMsg = Constant.subPack(request, IdlistAcceptMsg.class);
				if (acceptMsg == null) {
					return Constant.pack(ErrorCom.UNPACK_ERROR);
				}
				if (!acceptMsg.check()) {
					return Constant.pack(ErrorCom.PARSE_ERROR);
				}
	
			      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			      if (session == null) {
			          return Constant.pack(ErrorCom.GET_LOGIN);
			      }
	
		        List<Long> idList = acceptMsg.getIdList();
		        Return<List<Long>> ret = shopActivityService.batchDel(idList);
		        if (Return.isErr(ret)) {
		            return Constant.pack(ret);
		        }
		        
		        returnList = ret.getData();
			 } catch (Exception e) {
				logger.error(e.getMessage());
				return Constant.pack(ErrorCom.ERROR);
			 }			        

	        return Constant.pack(returnList);
	    }
}
