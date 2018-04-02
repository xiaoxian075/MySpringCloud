package com.scd.sdk;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.scd.sdk.util.DesUtil;

//@SpringBootApplication
public class SdkApplication {


	public static void main(String[] args) {
		//SpringApplication.run(SdkApplication.class, args);
		//System.out.println(GeneralValidationUtil.code("01258f", 6));
		//System.out.println(NetworkUtil.createSessionId());
//		for (int i=0; i<20; i++) 
//		System.out.println(RandomUtil.randInt(0, 1));
		
//		String data = MD5Util.encodeByMD5("21218cca77804d2ba1922c33e0151105");
//		System.out.println(data);
		//21218CCA77804D2BA1922C33E0151105
		//21218cca77804d2ba1922c33e0151105
		
		//46CC468DF60C961D8DA2326337C7AA58
		//46cc468df60c961d8da2326337c7aa58
		
		//ecf77aa883320e3bdb8c5e313815a6d6
		
		byte[] myByte = readFile("D:\\shuju.txt");	//D:\\sss.txt   D:\\1.jpg
//		String data = DesUtil.base64Encode(myByte);
		String data = new String(myByte);
		System.out.println(data);
		byte[] arrByte = DesUtil.base64Decode(data);
		writeByFileOutputStream(arrByte);
		
	}
	
	public static void writeByFileOutputStream(byte[] byteData) {

		FileOutputStream fop = null;
		File file;
		//String content = "This is the text content";
		try {
			file = new File("D:\\test.png");
			fop = new FileOutputStream(file);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// get the content in bytes
			//byte[] contentInBytes = data.getBytes();

			fop.write(byteData);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeFile(String data) {

//        FileOutputStream out = null;
        FileOutputStream outSTr = null;
        BufferedOutputStream Buff = null;
//        FileWriter fw = null;

        int count = 1000;//写文件行数

        try {
            //经过测试：FileOutputStream执行耗时:17，6，10 毫秒
//            out = new FileOutputStream(new File("d:\\test.png"));
//            long begin = System.currentTimeMillis();
//            for (int i = 0; i < count; i++) {
//                out.write("测试java 文件操作\r\n".getBytes());
//            }
//            out.close();
//            long end = System.currentTimeMillis();
//            System.out.println("FileOutputStream执行耗时:" + (end - begin) + " 毫秒");

            //经过测试：ufferedOutputStream执行耗时:1,1，1 毫秒
            outSTr = new FileOutputStream(new File("D:\\test.png"));
            Buff = new BufferedOutputStream(outSTr);
            long begin0 = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                Buff.write("测试java 文件操作\r\n".getBytes());
            }
            Buff.flush();
            Buff.close();
            long end0 = System.currentTimeMillis();
            System.out.println("BufferedOutputStream执行耗时:" + (end0 - begin0) + " 毫秒");

//            //经过测试：FileWriter执行耗时:3,9，5 毫秒
//            fw = new FileWriter("C:\\Users\\lee\\Desktop\\add2.txt");
//            long begin3 = System.currentTimeMillis();
//            for (int i = 0; i < count; i++) {
//                fw.write("测试java 文件操作\r\n");
//            }
//            fw.close();
//            long end3 = System.currentTimeMillis();
//            System.out.println("FileWriter执行耗时:" + (end3 - begin3) + " 毫秒");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
//                fw.close();
                Buff.close();
                outSTr.close();
//                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
	
	
	
	public static byte[] readFile(String fileName) {
		byte[] m_binArray = null;
		try {
			File localFile = new File(fileName);
			FileInputStream ins = new FileInputStream(localFile);
			int countLen = ins.available();
			m_binArray = new byte[countLen];
			ins.read(m_binArray);
			ins.close();
		} catch (Exception e) {
			return null;
		}
		
		return m_binArray;
	}
}
