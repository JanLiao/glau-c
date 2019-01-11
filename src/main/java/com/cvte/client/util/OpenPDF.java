package com.cvte.client.util;

import java.io.IOException;
import java.util.List;

import com.cvte.client.constant.Constant;
import com.cvte.client.gui.ClientGUI;

import javafx.application.Platform;
import javafx.stage.Stage;

public class OpenPDF {

	public static void readPDF(String filepath) {
		
		//先关闭之前打开的PDF文件   再关闭
		
		//D:/Program Files (x86)/Foxit Software/Foxit Reader/FoxitReader.exe
		if(Constant.list.size() == 0) {
			System.out.println("当前PDF打开数为空");
//			if(ClientGUI.area != null) {
//				  ClientGUI.area.appendText("\t\n" + "当前PDF打开数为空");
//			  }
//			if(Constant.MESSAGE != null) {
//				Constant.M1.setText(Constant.M2.getText());
//				Constant.M2.setText("当前PDF打开数为空");
//			}
			MessageUtil.updateMessage("当前PDF打开数为空");
			
//			Platform.runLater(() -> {
//				Constant.MESSAGE.close();
//			});
			if(Constant.MESSAGE != null) {
				Stage stage = Constant.MESSAGE;
				MyTask task = new MyTask();
				task.setStage(stage);
				new Thread(task).start();
			}
			Constant.MESSAGE = null;
			
			Process process = null;
			try { 
				//process = Runtime.getRuntime().exec(Constant.pdfRead + "  " +   filepath);
				process = Runtime.getRuntime().exec(PropertyUtil.Pdf_Read_Path + "  " +   filepath);
			    Constant.list.add(process);
			} catch (IOException e) { 
				e.printStackTrace(); 
			}
			
		}else {
			System.out.println("curr size = " + Constant.list.size());
			Process p = Constant.list.get(0);
			Constant.list.remove(0);
			p.destroy();
			System.out.println("摧毁城堡");
//			if(ClientGUI.area != null) {
//				  ClientGUI.area.appendText("\t\n" + "PDF即将打开");
//			  }
//			if(Constant.MESSAGE != null) {
//				Constant.M1.setText(Constant.M2.getText());
//				Constant.M2.setText("PDF即将打开");
//			}
			MessageUtil.updateMessage("PDF即将打开");
			
			if(Constant.MESSAGE != null) {
				Stage stage = Constant.MESSAGE;
				MyTask task = new MyTask();
				task.setStage(stage);
				new Thread(task).start();
			}
			Constant.MESSAGE = null;
			
			Process process = null;
			try { 
				//process = Runtime.getRuntime().exec(Constant.pdfRead + "  " +   filepath);
				process = Runtime.getRuntime().exec(PropertyUtil.Pdf_Read_Path + "  " +   filepath);
			    Constant.list.add(process);
//			    if(ClientGUI.area != null) {
//					  ClientGUI.area.appendText("\t\n" + "PDF已打开");
//				  }
			} catch (IOException e) { 
				e.printStackTrace(); } 
			}
		}

	public static void openPDF(String name) {
		List<String[]> list = ReadCSV.readCSV(PropertyUtil.Logger_Path + 
    			"/" + "logger.csv");
		Process process = null;
		for(String[] s : list) {
			if(name.equals(s[0])) {				
				try { 
					//process = Runtime.getRuntime().exec(Constant.pdfRead + "  " +   filepath);
					process = Runtime.getRuntime().exec(PropertyUtil.Pdf_Read_Path + "  " +   s[1]);
					Constant.list.add(process);
				} catch (IOException e) { 
					e.printStackTrace(); 
				}
				break;
			}
		}
	}
}
