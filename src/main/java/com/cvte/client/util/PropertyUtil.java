package com.cvte.client.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil {  //获得配置文件的属性
	
	//配置文件
	private static final String clientPropPath = "/client.properties";  
	
    public static String ServerIP;
    public static String ServerPORT;
    public static int ServerNettyPort;
    public static String SerialNumber;            //运行本程序的客户端序列号 
    public static String ConnectPassword;
	public static String TransferDirectoryPath;   //watch文件夹
	public static String Save_Pdf_Path;
	public static String Img_Result_Path;
	public static String Logger_Path;
	public static String Pdf_Read_Path;
	public static String rem;
	public static String Auto_Connection;
	public static String num;
	

	public boolean loadProperty() {
		
		try {
			Properties prop = new Properties();// 属性集合对象   
			String rootPath = System.getProperty("user.dir").replace("\\", "/");
			//System.out.println(rootPath);
			FileInputStream fis = new FileInputStream(rootPath + clientPropPath);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			prop.load(isr);// 将属性文件流装载到Properties对象中   
			fis.close();// 关闭流 
			
			
			ServerIP = prop.getProperty("Server_IP", "");
			ServerPORT = prop.getProperty("Server_PORT", "");
			ServerNettyPort = Integer.parseInt(prop.getProperty("Server_Netty_Port", ""));
			SerialNumber = prop.getProperty("Serial_Number", "");
			ConnectPassword = prop.getProperty("Connect_Password", "");
			TransferDirectoryPath = prop.getProperty("Transfer_Directory_Path", "");
			Save_Pdf_Path = prop.getProperty("Save_Pdf_Path", "");
			Img_Result_Path = prop.getProperty("Img_Result_Path", "");
			Logger_Path = prop.getProperty("Logger_Path", "");
			Pdf_Read_Path = prop.getProperty("Pdf_Read_Path", "");
			rem = prop.getProperty("rem", "");
			Auto_Connection = prop.getProperty("Auto_Connection", "");
			num = prop.getProperty("num", "");
		    
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
		
	public boolean updateProperty(String key, String value) {		
		try {    
			Properties prop = new Properties();
			String rootPath = System.getProperty("user.dir").replace("\\", "/");
			//System.out.println(rootPath);
			FileInputStream fis = new FileInputStream(rootPath + clientPropPath);
			prop.load(fis);// 将属性文件流装载到Properties对象中   
			fis.close();// 关闭流 
			
			String Pdf_Read_Path = prop.getProperty("Pdf_Read_Path", "");
			System.out.println(Pdf_Read_Path);
			
			prop.setProperty(key, value);
            FileOutputStream fos = new FileOutputStream(rootPath + clientPropPath);     
            // 将Properties集合保存到流中     
            prop.store(fos, "Copyright (c) SCUT");     
            fos.close();// 关闭流     
        } catch (FileNotFoundException e) {    
            // TODO Auto-generated catch block    
            e.printStackTrace();    
            return false;    
        } catch (IOException e) {    
            // TODO Auto-generated catch block    
            e.printStackTrace();    
            return false;    
        } 
		return false;
	}
	
	public static void main(String[] args) {
		PropertyUtil util = new PropertyUtil();
		Map<String, String> map = new HashMap<String, String>();
		map.put("Server_IP", "172.17.195.44");
		map.put("Server_PORT", "8090");
		map.put("Server_Netty_Port", "12125");
		map.put("Serial_Number", "1113");
		map.put("Connect_Password", "123456");
		map.put("Transfer_Directory_Path", "D:/NW400_imgs");
		map.put("Save_Pdf_Path", "D:/pdfFile");
		map.put("Img_Result_Path", "D:/fullCupFile");
		map.put("Logger_Path", "D:/logFile");
		map.put("Pdf_Read_Path", "C:/Program Files (x86)/Foxit Software/Foxit Reader/FoxitReader.exe");
		util.writePro(map);
		//util.updateProperty("Pdf_Read_Path", "C:/Program Files (x86)/Foxit Software/Foxit Reader/FoxitReader.exe");
	}

	public static void writePro(Map<String, String> map) {
		try{
			String rootPath = System.getProperty("user.dir").replace("\\", "/");
			File f5=new File(rootPath + clientPropPath);
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f5),"UTF-8"); 
			//FileWriter fw=new FileWriter(f5);
	        BufferedWriter bw=new BufferedWriter(osw);
	        for(String s: map.keySet()) {
	        	bw.write(s + "=" + map.get(s));
	        	bw.newLine();
	        }
			bw.close();
			osw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
