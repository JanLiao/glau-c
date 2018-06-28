package com.cvte.client.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH )+1;
		int date = cal.get(Calendar.DATE);
		String csvFile = "E:/csvFile";
		File f1 = new File(csvFile + "/" + year + "-" + month + "-" + date);
		if(!f1.exists()) {
			f1.mkdirs();
		}
		
		String filePath = csvFile + "/" + year + "-" + month + "-" + date + "/" + "a.csv";
		File f2 = new File(filePath);
		if(!f2.exists()) {
			try {
				f2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String filepath = "D:/";
		File f = new File(filepath);
		File[] ff = f.listFiles();
		for(File fff : ff) {
			
			System.out.println(fff.isDirectory());
			//System.out.println(fff.getName());
		}
		
	}
	
}
