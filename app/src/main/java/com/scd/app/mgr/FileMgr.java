package com.scd.app.mgr;

import com.scd.app.constant.Constant;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.RandomUtil;
import com.scd.sdk.util.pojo.FileInfo;
import com.scd.sdk.util.pojo.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

@SuppressWarnings({ "restriction", "unused" })
public class FileMgr {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final static String SOWING = "app_pic/";
	
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

	
	public Return<FileInfo> saveSowing(String picInfo) {
		return save(picInfo, SOWING);
	}
	
	public Return<FileInfo> save(String info,String context) {
		FileInfo fileInfo = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] p = decoder.decodeBuffer(info);
			String fileName = RandomUtil.getUuid() + ".jpg";
            File file = new File(path+SOWING, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            InputStream in = new ByteArrayInputStream(p);
            byte[] b = new byte[1024];
            int nRead = 0;
            while ((nRead = in.read(b)) != -1) {
                fos.write(b, 0, nRead);
            }
            fos.flush();
            fos.close();
            in.close();
            fileInfo = new FileInfo(fileName, context+fileName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.FILE_UPLOAD_ERROR);
		}
		return Constant.createReturn(fileInfo);
	}


	public String saveSowingStr(String picInfo) {
		return saveStr(picInfo, SOWING);
	}

	public String saveStr(String info,String context) {
		String fileName = "";
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] p = decoder.decodeBuffer(info);
			fileName = RandomUtil.getUuid() + ".jpg";
			File file = new File(path+SOWING, fileName);
			FileOutputStream fos = new FileOutputStream(file);
			InputStream in = new ByteArrayInputStream(p);
			byte[] b = new byte[1024];
			int nRead = 0;
			while ((nRead = in.read(b)) != -1) {
				fos.write(b, 0, nRead);
			}
			fos.flush();
			fos.close();
			in.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return context+fileName;
	}

	public static void main(String[] args) {
String info ="";
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] p = decoder.decodeBuffer(info);
			String fileName = RandomUtil.getUuid() + ".jpg";
			File file = new File("D:\\pic\\"+SOWING, fileName);
			FileOutputStream fos = new FileOutputStream(file);
			InputStream in = new ByteArrayInputStream(p);
			byte[] b = new byte[1024];
			int nRead = 0;
			while ((nRead = in.read(b)) != -1) {
				fos.write(b, 0, nRead);
			}
			fos.flush();
			fos.close();
			in.close();
		} catch (Exception e) {
		}
	}
}
