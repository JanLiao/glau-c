package com.cvte.client.constant;

import java.util.ArrayList;
import java.util.List;

import com.cvte.client.gui.Image;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;

import io.netty.channel.ChannelHandlerContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Constant {
	
	public static String pdfRead = "C:/Program Files (x86)/Foxit Software/Foxit Reader/FoxitReader.exe";
	
	public static List<Process> list = new ArrayList<Process>();
	
	public static Integer flag = 0;   //0正常状态  1客户端主动关闭
	
	public static List<JFXTreeTableColumn<Image, String>> columnList = 
			new ArrayList<JFXTreeTableColumn<Image, String>>();
	
	public static JFXTreeTableColumn<Image, Object> obj = null;
	
	public static JFXTreeTableView<Image> TreeView = null;
	
	public static int LoginFlag = 0;
	public static JFXButton btn = null;
	//public static EventHandler<ActionEvent> event = null;
	public static int ConnectionFlag = 0;  //0-断开  1-连接
	
	public static ChannelHandlerContext ctx = null;
	
	public static int num = 0;
}
