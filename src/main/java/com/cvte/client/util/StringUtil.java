package com.cvte.client.util;

public class StringUtil {

	
	public static  boolean isEmpty(String s) {  //字符串非空验证
		if(s == null || s.length() <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static String getFileName(String filePath) {  //通过文件路径获取文件名
		try {
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
			return fileName;
		} catch(Exception e) {
			return null;
		}
	}
	
	
}
