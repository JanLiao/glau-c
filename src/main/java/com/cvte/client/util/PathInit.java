package com.cvte.client.util;

import java.io.File;
import java.io.IOException;

public class PathInit {

	public static void pathInit() {
		File f = new File(PropertyUtil.Save_Pdf_Path);
		File f2 = new File(PropertyUtil.TransferDirectoryPath);
		File f4 = new File(PropertyUtil.Logger_Path);
		File ff1 = new File(PropertyUtil.Logger_Path + "/" + "logger.csv");
		if (!f4.exists()) {
			f4.mkdirs();
		}
		if(!ff1.exists()) {
			try {
				ff1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!f2.exists()) {
			f2.mkdirs();
		}
		if (!f.exists()) {
			f.mkdirs();
		}
		
	}
}
