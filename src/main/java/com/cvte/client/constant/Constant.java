package com.cvte.client.constant;

import java.util.ArrayList;
import java.util.List;

import com.cvte.client.gui.Image;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;

public class Constant {
	
	public static String pdfRead = "C:/Program Files (x86)/Foxit Software/Foxit Reader/FoxitReader.exe";
	
	public static List<Process> list = new ArrayList<Process>();
	
	public static Integer flag = 0;   //0正常状态  1客户端主动关闭
	
	public static List<JFXTreeTableColumn<Image, String>> columnList = 
			new ArrayList<JFXTreeTableColumn<Image, String>>();
	
	public static JFXTreeTableColumn<Image, Object> obj = null;
	
	public static JFXTreeTableView<Image> TreeView = null;
	
}
