package com.cvte.client.util;

import com.cvte.client.bootstrap.NettyClient;
import com.cvte.client.bootstrap.NettyClientHandler;
import com.cvte.client.constant.Constant;

public class TestThread {

	public static void main(String[] args) {
		Thread conThread = new Thread(new Runnable() {

			@Override
			public void run() {
				PropertyUtil propertyUtil = new PropertyUtil();
		        if(propertyUtil.loadProperty()) {
		    	   NettyClient nettyClient = new NettyClient();
		           try {
					nettyClient.connect(PropertyUtil.ServerNettyPort, PropertyUtil.ServerIP);  
				} catch (Exception e) {
					e.printStackTrace();
				}
		        }
			}
    		
    	});
    	conThread.start();
    	
    	System.out.println(6666666);
    	
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	Constant.flag = 1;
    	//NettyClientHandler.channelcx.close();
		NettyClientHandler.channelcx.channel().close();
    	conThread.interrupt();
    	System.out.println("is connect=" + conThread.isAlive());
    	System.out.println("is interrupt=" + conThread.isInterrupted());
    	System.out.println("close");
	}
	
}
