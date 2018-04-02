package com.scd.coredb.feign;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.service.Kd100Service;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.Kd100Po;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/kd100")
public class FKd100 {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Kd100Service kd100Service;
	
	@RequestMapping(value = "/list")
	public Return<PageInfo<Kd100Po>> list(int page, int size) {
		Return<PageInfo<Kd100Po>> ret = null;
		try{
			ret = kd100Service.list(page, size);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}

	@RequestMapping(value = "/del")
	public Return<Long> del(long id) {
		Return<Long> ret = null;
		try{
			ret = kd100Service.del(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}

	@RequestMapping(value = "/batchDel")
	public Return<List<Long>> batchDel(@RequestBody List<Long> idList) {
		Return<List<Long>> ret = null;
		try{
			ret = kd100Service.batchDel(idList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}

	@RequestMapping(value = "/add")
	public Return<Object> add(String name, String code) {
		Return<Object> ret = null;
		try{
			ret = kd100Service.add(name, code);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}


	@RequestMapping(value = "/edit")
	public Return<Object> edit(long id, String name, String code) {
		Return<Object> ret = null;
		try{
			ret = kd100Service.edit(id, name, code);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}
	
    @RequestMapping(value = "/getAll")
	public Return<List<Kd100Po>> getAll() {
		Return<List<Kd100Po>> ret = null;
		try{
			ret = kd100Service.getAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
    }
}


