package com.scd.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scd.admin.constant.Constant;
import com.scd.admin.mgr.FileMgr;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.FileInfo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/file")
public class FileController {
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, MultipartFile mulFile) {
		if (mulFile == null) {
			return Constant.pack(ErrorCom.FILE_UPLOAD_ERROR); 
		}
		Return<FileInfo> ret = FileMgr.getInstance().saveSowing(mulFile);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		return Constant.pack(ret.getData());
	}
	
//	@RequestMapping(value = "/upload", method = RequestMethod.POST)
//	public String upload(HttpServletRequest request, MultipartFile mulFile) {
//		if (mulFile == null) {
//			return Constant.pack(ErrorCom.FILE_UPLOAD_ERROR); 
//		}
//		Return<FileInfo> ret = FileMgr.getInstance().saveSowing(mulFile);
//		if (Return.isErr(ret)) {
//			return Constant.pack(ret);
//		}
//
//		Map<String,String> mapData = new HashMap<String,String>();
//		mapData.put("url", FileMgr.getInstance().getBaseUrl() + ret.getData().getUrl());
//		String data = GsonUtil.toString(mapData);
//		return data;
//	}
	
//	@RequestMapping(value = "/filesUpload", method = RequestMethod.POST)
//	public String filesUpload(HttpServletRequest request, MultipartFile[] mulFile) {
//		if (mulFile == null) {
//			return Constant.pack(ErrorCom.FILE_UPLOAD_ERROR); 
//		}
////		Return<FileInfo> ret = FileMgr.getInstance().saveSowing(mulFile);
////		if (Return.isErr(ret)) {
////			return Encrypt.getInstance().pack(ret.getCode(), ret.getDesc());
////		}
////		return Encrypt.getInstance().pack(ret.getData());
//		return Constant.pack();
//	}
	
}
