package com.scd.async.controller;

import com.scd.async.mgr.Rabbitmq;
import com.scd.joggle.mq.ChitData;
import com.scd.sdk.util.GsonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;



@RestController
public class TestController {
	
	/**
	 * 接口测试
	 * @return
	 */
    @RequestMapping("/hello"/*,method = RequestMethod.GET*/)
    public String sayHello() {
        return "async say hello";
    }
    
	@RequestMapping("/sendChit")
	public String sendChit(@RequestParam(value = "id") int id, @RequestParam(value = "phone") String phone, @RequestParam(value = "code") String code) {
		List<String> phoneList = new ArrayList<String>();
		phoneList.add(phone);
		ChitData chitData = new ChitData(id, phoneList, code);
		String data = GsonUtil.toString(chitData);
		if (data == null) {
			return "格式错误";
		}
		
		if (Rabbitmq.getInstance().sendChitMsg(data)) {
			return "发送成功";
		} else {
			return "发送失败";
		}
	}
    
}
