package com.scd.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.feign.FCarousel;
import com.scd.admin.pojo.util.CarouselUtil;
import com.scd.admin.pojo.vo.CarouselVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CarouselPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class CarouselService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FCarousel fCarousel;

	public Return<PageInfo<CarouselVo>> list(int page, int size) {	
		PageInfo<CarouselVo> kdPage = null;
		try {
			Return<PageInfo<CarouselPo>> ret = fCarousel.list(page, size);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			
			PageInfo<CarouselPo> pageInfo = ret.getData();
			List<CarouselPo> poList = pageInfo.getList();
			List<CarouselVo> voList = CarouselUtil.change(poList);
			
			kdPage = new PageInfo<CarouselVo>(pageInfo.getPage(), pageInfo.getTotal(), voList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return Constant.createReturn(kdPage);
	}
	

	public Return<Object> add(int type, String url) {
		try {
			Return<Object> ret = fCarousel.add(type, url);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn();
	}


	public Return<Object> edit(long id, int type, String url) {
		try {
			Return<Object> ret = fCarousel.edit(id, type, url);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn();
	}

	
	public Return<Long> del(long id) {
		long returnId = 0;
		try {
			Return<Long> ret = fCarousel.del(id);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			returnId = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn(returnId);
	}
	
	@Transactional
	public Return<List<Long>> batchDel(List<Long> idList) {
		List<Long> returnIdList = null;
		try {
			Return<List<Long>> ret = fCarousel.batchDel(idList);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			returnIdList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn(returnIdList);
	}

}

