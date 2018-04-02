package com.scd.coredb.feign;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.pojo.db.TAddress;
import com.scd.coredb.service.AddressService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.AddressPo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/address")
public class FAddress {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private AddressService addressService;
	
	@RequestMapping(value = "/getListByAccount")
    public Return<List<AddressPo>> getListByAccount(String account) {
		List<AddressPo> poList = null;
    	try {
    		
			Return<List<TAddress>> ret = addressService.findByAccount(account);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			List<TAddress> addressList = ret.getData();
			if (addressList == null) {
				return Constant.createReturn(ErrorCom.NOT_EXIST);
			}
			poList = TAddress.change(addressList);	    	
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return Constant.createReturn(poList);
    }

	@RequestMapping(value = "/add")
    public Return<AddressPo> add(@RequestBody AddressPo addressPo) {
		TAddress tAddress = TAddress.createT(addressPo);
		
		AddressPo newAddressPo = null;
    	try {
    		Return<TAddress> ret = addressService.add(tAddress);
	    	if (Return.isErr(ret)) {
	    		return Constant.createReturn(ret.getCode(), ret.getDesc());
	    	}
	    	TAddress newTAddress = ret.getData();
	    	if (newTAddress == null) {
	    		return Constant.createReturn(ErrorCom.NOT_EXIST);
	    	}
			newAddressPo = newTAddress.createPo();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
		return Constant.createReturn(newAddressPo);
    }
	
	@RequestMapping(value = "/edit")
    public Return<AddressPo> edit(@RequestBody AddressPo addressPo) {
		AddressPo newAddressPo = null;
    	try {
    		Return<TAddress> ret = addressService.edit(addressPo);
	    	if (Return.isErr(ret)) {
	    		return Constant.createReturn(ret.getCode(), ret.getDesc());
	    	}
	    	TAddress newTAddress = ret.getData();
	    	if (newTAddress == null) {
	    		return Constant.createReturn(ErrorCom.NOT_EXIST);
	    	}
			newAddressPo = newTAddress.createPo();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
    	
    	return Constant.createReturn(newAddressPo);
    }

	@RequestMapping(value = "/del")
    public Return<Object> del(long id) {
		AddressPo po = null;
		try {
			Return<TAddress> ret = addressService.del(id);
	    	if (Return.isErr(ret)) {
	    		return Constant.createReturn(ret.getCode(), ret.getDesc());
	    	}
	    	
	    	TAddress tAddress = ret.getData();
	    	if (tAddress == null) {
	    		return Constant.createReturn(ErrorCom.NOT_EXIST);
	    	}
	    	po = tAddress.createPo();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		} 
    	
		return Constant.createReturn(po);
    }
	
	@RequestMapping(value = "/setDefault")
    public Return<Object> setDefault(long id, int state) {
		AddressPo po = null;
		try {
			Return<TAddress> ret = addressService.setDefault(id, state);
	    	if (Return.isErr(ret)) {
	    		return Constant.createReturn(ret.getCode(), ret.getDesc());
	    	}
	    	
	    	TAddress tAddress = ret.getData();
	    	if (tAddress == null) {
	    		return Constant.createReturn(ErrorCom.NOT_EXIST);
	    	}
	    	po = tAddress.createPo();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		} 
    	
		return Constant.createReturn(po);
    }
	
    @RequestMapping(value = "/getDefault")
    public Return<AddressPo> getDefault(String account) {
    	AddressPo po = null;
		try {
			Return<AddressPo> ret = addressService.getDefault(account);
			if (Return.isErr(ret)) {
				return Constant.createReturn(ret.getCode(), ret.getDesc());
			}
			po = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return Constant.createReturn(po);
    }
}
