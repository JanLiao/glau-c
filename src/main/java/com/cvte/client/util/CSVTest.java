package com.cvte.client.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import com.csvreader.CsvWriter;

public class CSVTest {

	public static void writeCSV() {  
	    // 定义一个CSV路径  
	    String csvFilePath = "E://StemQ.csv";  
	    try {  
	    	 BufferedWriter out = new BufferedWriter(new 

	    	           OutputStreamWriter(new FileOutputStream(csvFilePath,true),"UTF-8"),1024);
	        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);  
	        CsvWriter csvWriter = new CsvWriter(out, ',');  
	        // 写表头  
	        String[] csvHeaders = { "编号", "姓名", "年龄" };  
	        //csvWriter.writeRecord(csvHeaders);  
	        // 写内容  
	        for (int i = 0; i < 20; i++) {  
	            String[] csvContent = { i + "000000", "StemQ", "1" + i };  
	            csvWriter.writeRecord(csvContent);  
	        }  
	        csvWriter.close();  
	        System.out.println("--------CSV文件已经写入--------");  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	
	public static void main(String[] args) {
		writeCSV();
	}
	
}
