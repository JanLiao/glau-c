package com.cvte.client.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Constant1 {
	
	//线程池
	public static ExecutorService cacheThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 15, TimeUnit.MINUTES, new SynchronousQueue<Runnable>());
	
	
    private static String terminalId = "";  //终端登录成功后, 服务器返回识别该终端的id
    
    private static String ImgServerPath = "";
  
    
    public static String getImgServerPath() {
		return ImgServerPath;
	}

	public static void setImgServerPath(String imgServerPath) {
		ImgServerPath = imgServerPath;
	}

	public static String getTerminalId() {
		return terminalId;
	}

	public static void setTerminalId(String terminalId) {
		Constant1.terminalId = terminalId;
	}
}
