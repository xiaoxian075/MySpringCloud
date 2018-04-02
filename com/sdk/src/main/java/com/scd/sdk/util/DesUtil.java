package com.scd.sdk.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 DES加密介绍
      DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现。
 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 */
/**
 * @ClassName DesUtil
 * @Description DES加密
 * @author chenjx
 * @date 2017年12月6日
 */
public class DesUtil {
	
	/**
     * 加密 
     * 以字符串(base64)返回
     * @param datasource String
     * @param password String
     * @return String
     */
	public static String encrypt(String datasource, String password) {
		byte[] arrByte;
		try {
			byte[] encrypt = encrypt(datasource.getBytes("utf-8"), password.getBytes());
			String data = byteArr2HexStr(encrypt);
			arrByte = data.toString().getBytes("utf-8");
		} catch (Exception e) {
			return null;
		}
		if (arrByte == null) {
			return null;
		}
		return base64Encode(arrByte);
	}
	
    /**
     * 解密
     * 对字符串(base64)进行解析
     * @param src String
     * @param password String
     * @return String
     */
	public static String decrypt(String src, String password) {
		if (src == null || src.length() == 0 || password == null || password.length() == 0) {
			return src;
		}
		byte[] arrByte = null;
		try {
			byte[] byteSrc = base64Decode(src);
			byte[] data = hexStr2ByteArr(byteSrc);
			arrByte = decrypt(data, password.getBytes());
			if (arrByte == null) {
				return null;
			}
		} catch(Exception e) {

		}
		return new String(arrByte);
	}
	
	/**
	 * Description 根据键值进行解密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decryptDueBase64(String data, String key) {
		if (data == null) {
			return null;
		}

		try {
			byte[] buf = base64Decode(data);
			byte[] bt = decrypt(buf,key.getBytes());
			return new String(bt, "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

    /**
     * 加密
     * @param datasource byte[]
     * @param password byte[]
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, byte[] password) {
    	if (datasource == null || password == null) {
			return datasource;
		}
        try{
	        SecureRandom random = new SecureRandom();
	        DESKeySpec desKey = new DESKeySpec(password);
	        //创建一个密匙工厂，然后用它把DESKeySpec转换成
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        SecretKey securekey = keyFactory.generateSecret(desKey);
	        //Cipher对象实际完成加密操作
	        Cipher cipher = Cipher.getInstance("DES");
	        //用密匙初始化Cipher对象
	        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
	        //现在，获取数据并加密
	        //正式执行加密操作
	        return cipher.doFinal(datasource);
        }catch(Throwable e){
        	return null;
        }
    }

    /**
     * 解密
     * @param src byte[]
     * @param password byte[]
     * @return byte[]
     */
    public static byte[] decrypt(byte[] src, byte[] password) {
    	try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(password);
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            // 真正开始解密操作
            return cipher.doFinal(src);
    	} catch(Throwable e) {
    		return null;
    	}
    }
    


	public static String base64Encode(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

//	public static byte[] base64Decode(String base64Code) {
//		return Base64.getDecoder().decode(base64Code);
//	}
	
//  public static byte[] base64EncodeToByte(byte[] bytes) {
//		return Base64.getEncoder().encode(bytes);
//	}
	public static byte[] base64Decode(String base64Code){
		try {
			return Base64.getDecoder().decode(base64Code.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static String base64EncodeFromStr(String data) {
		try {
			byte[] bytes = data.getBytes();
			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String base64DecodeToStr(String base64Code){
		try {
			byte[] arrByte = Base64.getDecoder().decode(base64Code.getBytes("utf-8"));
			return new String(arrByte);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     * @param arrB  需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }
    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(byte[] strIn) throws Exception {
        //byte[] arrB = strIn.getBytes();
        int iLen = strIn.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(strIn, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
	
	public static void main(String[] args) {
		String password = "abcdefgh";
		
		// 原始字符串
		String data = "{\"sessionId\":\"1\",\"requestId\":\"1\",\"sign\":\"\",\"info\":\"{\\\"nickName\\\":\\\"18200000090\\\"}\"}";
		System.out.println("原如字符串 ：" + data);

		//加密
		String encrypt = encrypt(data, password);
		System.out.println("加密后字符串：" + encrypt);
		
		// 解密
		String decrypt = decrypt(encrypt, password);
		System.out.println("解密后字符串：" + decrypt);
	}


}
