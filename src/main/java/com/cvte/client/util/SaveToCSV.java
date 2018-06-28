package com.cvte.client.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Calendar;

import org.slf4j.Logger;

import com.csvreader.CsvWriter;
import com.cvte.client.gui.ClientGUI;
import com.cvte.netty.msg.ImgInfo;
import com.cvte.netty.msg.LoggerInfo;

public class SaveToCSV {

	public static void save(ImgInfo info) {
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH )+1;
		int date = cal.get(Calendar.DATE);
		String csvFile = PropertyUtil.Save_Pdf_Path;
		File f1 = new File(csvFile + "/" + year + "-" + month + "-" + date);
		if(!f1.exists()) {
			f1.mkdirs();
		}
		
		String uid = info.getUid();
		String filePath = csvFile + "/" + year + "-" + month + "-" + date + "/" + uid + ".csv";
		File f2 = new File(filePath);
		if(!f2.exists()) {
			try {
				f2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			writeCSV(info, filePath);
		}else {
			appendCSV(info, filePath);
		}
		
	}

	public static void writeCSV(ImgInfo info, String filePath) {  
	    // 定义一个CSV路径  
	    try {  
	    	CsvWriter csvWriter = new CsvWriter(filePath,',',Charset.forName("UTF-8"));
	        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);  
	       // CsvWriter csvWriter = new CsvWriter(out, ',');  
	        // 写表头  
	        String[] csvHeaders = { "UID", "PID", "L/R","Original_img","Segent_img","OD","OC","CDR",
	        		"Zoom_img","Qulity", "AMD","DR","Glaucoma","Myopia"};  
	        csvWriter.writeRecord(csvHeaders);  
	        // 写内容  
	        String[] seg = info.getSegmentedImg().split("/");
	        String[] zoom = info.getZoomImg().split("/");
	        String segPath = PropertyUtil.Img_Result_Path + "/" + seg[1];
	        String zoomPath = PropertyUtil.Img_Result_Path + "/" + zoom[1];
	        String[] csvContent = {info.getUid(), info.getPid(), info.getLr(),info.getOriginalImg(), segPath
	        		,info.getOd(), info.getOc(), info.getCdr(), zoomPath,info.getQulity(), info.getAmd(),
	        		info.getDr(), info.getGlaucoma(), info.getMyopia()};
	        
	        csvWriter.writeRecord(csvContent);  
	        csvWriter.close();  
	        System.out.println("--------CSV文件已经写入--------");  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	
	public static void writeLoggerCSV(LoggerInfo log, String filePath) {  
	    // 定义一个CSV路径  
		System.out.println("filePath" + filePath);
	    try {  
	    	CsvWriter csvWriter = new CsvWriter(filePath,',',Charset.forName("UTF-8"));
	        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);  
	       // CsvWriter csvWriter = new CsvWriter(out, ',');  
	        // 写表头  
	        String[] csvHeaders = { "file_name", "report_name", "isProcessed","process_time"};  
	        csvWriter.writeRecord(csvHeaders);  
	        System.out.println("filename" + csvHeaders[0]);
	        // 写内容 
	        Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH )+1;
			int date = cal.get(Calendar.DATE);
	        String reportPath = PropertyUtil.Save_Pdf_Path + "/" + year + "-" + month + "-" + date + "/" + log.getReportName();
	        String[] csvContent = {log.getFileName(), reportPath, log.getIsProcessed(), log.getProcessTime()};
	        System.out.println("csvContent=" + csvContent[0]);
	        csvWriter.writeRecord(csvContent);  
	        csvWriter.close();  
	        System.out.println("--------Logger CSV文件已经写入--------");  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	
	public static void appendLoggerCSV(LoggerInfo log, String filePath) {  
	    // 定义一个CSV路径  
	    //String csvFilePath = "E://StemQ.csv";  
	    try {  
	    	 BufferedWriter out = new BufferedWriter(new 

	    	           OutputStreamWriter(new FileOutputStream(filePath,true),"UTF-8"),1024);
	        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);  
	        CsvWriter csvWriter = new CsvWriter(out, ',');  
	        // 写表头  
	        //String[] csvHeaders = { "编号", "姓名", "年龄" };  
	        //csvWriter.writeRecord(csvHeaders);  
	        // 写内容  
	        Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH )+1;
			int date = cal.get(Calendar.DATE);
	        String reportPath = PropertyUtil.Save_Pdf_Path + "/" + year+ "-" +month + "-" + date + "/" + log.getReportName();
	        String[] csvContent = {log.getFileName(), reportPath, log.getIsProcessed(), log.getProcessTime()};
	        csvWriter.writeRecord(csvContent);
	        csvWriter.close();  
	        System.out.println("--------Logger CSV文件已经append写入--------");  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	
	public static void appendCSV(ImgInfo info, String filePath) {  
	    // 定义一个CSV路径  
	    //String csvFilePath = "E://StemQ.csv";  
	    try {  
	    	 BufferedWriter out = new BufferedWriter(new 

	    	           OutputStreamWriter(new FileOutputStream(filePath,true),"UTF-8"),1024);
	        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);  
	        CsvWriter csvWriter = new CsvWriter(out, ',');  
	        // 写表头  
	        //String[] csvHeaders = { "编号", "姓名", "年龄" };  
	        //csvWriter.writeRecord(csvHeaders);  
	        // 写内容  
	        String[] seg = info.getSegmentedImg().split("/");
	        String[] zoom = info.getZoomImg().split("/");
	        String segPath = PropertyUtil.Img_Result_Path + "/" + seg[1];
	        String zoomPath = PropertyUtil.Img_Result_Path + "/" + zoom[1];
	        String[] csvContent = {info.getUid(), info.getPid(), info.getLr(),info.getOriginalImg(), segPath
	        		,info.getOd(), info.getOc(), info.getCdr(), zoomPath,info.getQulity(), info.getAmd(),
	        		info.getDr(), info.getGlaucoma(), info.getMyopia()};
	        csvWriter.writeRecord(csvContent);
	        csvWriter.close();  
	        System.out.println("--------CSV文件已经写入--------");  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}

	public static void saveLog(LoggerInfo log) {
//		Calendar cal = Calendar.getInstance();
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH )+1;
//		int date = cal.get(Calendar.DATE);
		String csvFile = PropertyUtil.Logger_Path;
//		File f1 = new File(csvFile + "/" + year + "-" + month + "-" + date);
//		if(!f1.exists()) {
//			f1.mkdirs();
//		}

		System.out.println("logger=" + log);
		if(ClientGUI.area != null) {
			  ClientGUI.area.appendText("\t\n" + "logger=" + log);
		  }
		String filePath = csvFile + "/" +  "logger.csv";
		File f2 = new File(filePath);
		if(!f2.exists()) {
			try {
				f2.createNewFile();
				System.out.println("is create");
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("start to write");
			writeLoggerCSV(log, filePath);
			if(ClientGUI.area != null) {
				  ClientGUI.area.appendText("\t\n" + "logger is writed");
			  }
		}else {
			System.out.println("start append");
			appendLoggerCSV(log, filePath);
			if(ClientGUI.area != null) {
				  ClientGUI.area.appendText("\t\n" + "logger is writed");
			  }
		}
		
	}

}
