package com.scd.admin.mgr;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.scd.admin.constant.Constant;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.RandomUtil;
import com.scd.sdk.util.pojo.FileInfo;
import com.scd.sdk.util.pojo.Return;

public class FileMgr {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final static String SOWING = "sowing/";
	
	private FileMgr() {
	}
	private static class FileMgrFactory {
		private static FileMgr instance = new FileMgr();
	}
	public static FileMgr getInstance() {
		return FileMgrFactory.instance;
	}
	
	private String path;
	private String baseUrl;
	
	public void init(String path, String baseUrl) {
		this.path = path;
		this.baseUrl = baseUrl;
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
	
	public Return<FileInfo> saveSowing(MultipartFile mulFile) {
		return save(mulFile, SOWING);
	}
	
	public Return<FileInfo> save(MultipartFile mulFile, String context) {
		FileInfo fileInfo = null;
		try {
			String orgName = mulFile.getOriginalFilename();
			String fileName = getNewFileName(orgName);
			mulFile.transferTo(new File(path + context + fileName));
			fileInfo = new FileInfo(fileName, context + fileName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.FILE_UPLOAD_ERROR);
		}
		
		return Constant.createReturn(fileInfo);
	}
	
	private static String getNewFileName(String orgName) {
		String newName = RandomUtil.getUuid();

		if (orgName == null || orgName.length() == 0) {
			return newName;
		}
		String[] args = orgName.split("\\.");
		String endFile = null;
		if (args.length>1) {
			endFile = args[args.length-1];
			if (endFile != null && endFile.length() > 0) {
				newName += "." + endFile;
			}
		}
		return newName;
	}
}
