package com.cvte.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5Util {
	

	public static BigInteger getMD5(String path) {
		BigInteger bigIntMD5 = null;
		try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            bigIntMD5 = new BigInteger(1, md.digest());
            //System.out.println("文件md5值：" + bigIntMD5.toString(16));
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bigIntMD5;
	}
	
}
