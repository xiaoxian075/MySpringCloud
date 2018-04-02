package com.scd.mweb.mgr;

import java.util.HashMap;
import java.util.Map;

import com.scd.mweb.pojo.bo.RegisterCodeBo;

public class RegisterMgr {
    private RegisterMgr() {
    }
    private static class RegisterMgrFactory {
        private static RegisterMgr instance = new RegisterMgr();
    }
    public static RegisterMgr getInstance() {
        return RegisterMgrFactory.instance;
    }
    
    private static Map<String, RegisterCodeBo> mapRegister = new HashMap<String, RegisterCodeBo>();
    
    public RegisterCodeBo get(String phone) {
    	return mapRegister.get(phone);
    }
    
    public void add(RegisterCodeBo register) {
    	mapRegister.put(register.getPhone(), register);
    }
    
    public RegisterCodeBo remove(String phone) {
    	return mapRegister.remove(phone);
    }
    
}
