package com.cvte.client.util;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility that downloads a file from a URL.
 *
 */
public class HttpDownloadUtility {
	
//	private static final String saveDir = "D:/itmsFile";
	
//	public static void main(String args[]) throws Exception {
//		List<String> fileIds = new ArrayList<>();
//		fileIds.add("122");
//		reqDownload(fileIds);
//	}
//	
	
	private final static Logger logger = LoggerFactory.getLogger(HttpDownloadUtility.class);
	
	public static boolean  reqDownload(List<String> fileIds) throws Exception {
		//String path = "http://" + PropertyUtil.ServerIP +  ":8080/ITMS/downloadController/downloadZip.do?fileIds=";
		//String path = "http://" + PropertyUtil.ServerIP +  "/ITMS/downloadController/downloadZip.do?fileIds=";
		String path = "";
		if("".equals(PropertyUtil.ServerPORT)) {
			path = "http://" + PropertyUtil.ServerIP +  "/ITMS/downloadController/downloadZip.do?fileIds=";
		}else {
			path = "http://" + PropertyUtil.ServerIP + ":" + PropertyUtil.ServerPORT +  "/ITMS/downloadController/downloadZip.do?fileIds=";
		}
		
		System.out.println("path = " + path);
		System.out.println("SaveDirectoryPath = " + PropertyUtil.Save_Pdf_Path);
		return downloadFile(path + convertToCommaSeparated(fileIds), PropertyUtil.Save_Pdf_Path);
	}
	
	
	
	//将字符串集合转换成逗号分隔的
	private static final String Separated = ",";
	
	private static String convertToCommaSeparated(List<String> items) {
	  StringBuilder output = new StringBuilder();
	  for (String item : items)
	  {
	    output.append(item + Separated);
	  }

	  // Remove leading comma, if the list is not empty.
	  if (output.length() != 0)
	  {
	    output.deleteCharAt(output.length() - 1);
	  }

	  return output.toString();
	}
	
	
	
	
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Downloads a file from a URL
	 * @param fileURL HTTP URL of the file to be downloaded
	 * @param saveDir path of the directory to save the file
	 * @throws IOException
	 */
	public static boolean downloadFile(String fileURL, String saveDir)
			throws IOException {
		System.out.println("fileURL=" + fileURL);
		System.out.println("saveDir = " + saveDir);
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();
		System.out.println("code= " + responseCode);

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 9, disposition.length());
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
			}

			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;
			System.out.println("saveFilePath=" + saveFilePath);
			
			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("File downloaded");
			httpConn.disconnect();
			
			//解压zip文件
			unZip(saveFilePath, saveDir);
			//删除zip文件
			new File(saveFilePath).delete();
			
			return true;
		} else {
			httpConn.disconnect();
			logger.info("No file to download. Server replied HTTP code: " + responseCode);
			return false;
		}
	}
	
	
	
	/***
	   * 解压zip文件
	     * @param srcPath  zip文件地址
	     * @param destPath  解压后存放的地址
	     * @param includeZipFileName  是否包含zip文件名
	     */
    public static void unZip(String srcPath ,String destPath) {
       try {  
	        ZipInputStream Zin=new ZipInputStream(new FileInputStream(srcPath));
	        BufferedInputStream Bin=new BufferedInputStream(Zin);  
	        File Fout=null;  
	        ZipEntry entry;  
	        try {  
	            while((entry = Zin.getNextEntry())!=null){  
	                if(entry.isDirectory())continue; 
	                Fout=new File(destPath,entry.getName());  
	                if(!Fout.exists()){  
	                    (new File(Fout.getParent())).mkdirs();  
	                }  
	                FileOutputStream out=new FileOutputStream(Fout);  
	                BufferedOutputStream Bout=new BufferedOutputStream(out);  
	                int b;  
	                while((b=Bin.read())!=-1){  
	                    Bout.write(b);  
	                }  
	                Bout.close();  
	                out.close();      
	            }  
	            Bin.close();  
	            Zin.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	      } catch (FileNotFoundException e) {  
	           e.printStackTrace();  
	      }  
    }
	
	
}