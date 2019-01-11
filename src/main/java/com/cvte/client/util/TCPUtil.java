package com.cvte.client.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.netty.channel.ChannelHandlerContext;

/**
* @author jan
* @data 2018年9月26日 上午11:56:19
*/
public class TCPUtil {

	public static void sendImg(ChannelHandlerContext ctx) {
		File[] files = new File("D:/tmp").listFiles();
        for(File f : files) {
        	//send output msg  
            String imgData = getImageData(f.getPath(), f.getName());
            //String outMsg = "TCP connecting to " + TCP_SERVER_PORT + System.getProperty("line.separator");  
            System.out.println("," + imgData.length() + "," + f.getName());
            //ctx.flush();
            int index = imgData.indexOf(",");
            System.out.println("index=" + index);
            ctx.writeAndFlush("," + imgData.length() + "," + f.getName() + "," + imgData);//发送数据  
            //System.out.println("TcpClient sent: " + imgData);  
            //accept server response  
//            String inMsg = in.readLine() + System.getProperty("line.separator");//得到服务器返回的数据  
//            System.out.println("TcpClient received: " + inMsg);
//            System.out.println("TcpClient received size = " + inMsg.length());
            
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static String getImageData(String imgpath, String imgname) {
    	//String imgname = "阿热孜古丽．买买提_20180426102709108.jpg";
    	//imgname = "head.jpg";
    	//String imgpath = "D:/demo展示/阿热孜古丽．买买提_20180426102709108.jpg";
    	//imgpath = "D:/demo展示/head.jpg";
    	BufferedImage bufferedImage = null;
    	try {
    		bufferedImage = ImageIO.read(new File(imgpath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    	String dataURI = "";
		String[] str = imgname.split("[.]");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, str[1], baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		String imageMimeType = "image/" + str[1]; // Replace this for the correct mime of the image
		//dataURI = "data:" + imageMimeType + ";base64," + javax.xml.bind.DatatypeConverter.printBase64Binary(bytes);
		//System.out.println("dataURI = " + javax.xml.bind.DatatypeConverter.printBase64Binary(bytes));
		//System.out.println("size = " + javax.xml.bind.DatatypeConverter.printBase64Binary(bytes).length());
		return javax.xml.bind.DatatypeConverter.printBase64Binary(bytes);
    }
}
