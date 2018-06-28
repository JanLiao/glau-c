package com.cvte.client.util;


import java.io.File;
import java.util.Calendar;

import com.cvte.netty.msg.TransferMsg;

public class TransferFileHandler {

	public static boolean saveImage(TransferMsg transferMsg) {
		
		Calendar cal = Calendar.getInstance();
		 int year = cal.get(Calendar.YEAR);
		 int month = cal.get(Calendar.MONTH )+1;
		 int date = cal.get(Calendar.DATE);
		 int hour = cal.get(Calendar.HOUR);
		 int min = cal.get(Calendar.MINUTE);
		 int second = cal.get(Calendar.SECOND);
		 String pdfpath = PropertyUtil.Save_Pdf_Path + "/" + year +"-" + month + "-" + date;
		 File pdf = new File(pdfpath);
		 if(!pdf.exists()) {
			 pdf.mkdirs();
		 }
		
		//解压
		  byte[] unGZipBytes = FileUtil.unGZip(transferMsg.getAttachment());
		  boolean success = FileUtil.bytesToFile(unGZipBytes, pdfpath, transferMsg.getFileName().split(",")[0]);
	      return success;
	}

}
