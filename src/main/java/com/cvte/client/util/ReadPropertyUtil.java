package com.cvte.client.util;

import java.util.HashMap;
import java.util.Map;

public class ReadPropertyUtil {

	public static Map<String, String> readPro() {
		PropertyUtil util = new PropertyUtil();
		Map<String, String> map = new HashMap<String, String>();
		if(util.loadProperty()) {
			map.put("Server_IP", PropertyUtil.ServerIP);
			map.put("Server_PORT", PropertyUtil.ServerPORT);
			map.put("Server_Netty_Port", "" + PropertyUtil.ServerNettyPort);
			map.put("Serial_Number", PropertyUtil.SerialNumber);
			map.put("Connect_Password", PropertyUtil.ConnectPassword);
			map.put("Transfer_Directory_Path", PropertyUtil.TransferDirectoryPath);
			map.put("Save_Pdf_Path", PropertyUtil.Save_Pdf_Path);
			map.put("Img_Result_Path", PropertyUtil.Img_Result_Path);
			map.put("Logger_Path", PropertyUtil.Logger_Path);
			map.put("Pdf_Read_Path", PropertyUtil.Pdf_Read_Path);
			map.put("rem", PropertyUtil.rem);
			map.put("Auto_Connection", PropertyUtil.Auto_Connection);
			map.put("num", PropertyUtil.num);
		}
		return map;
	}

}
