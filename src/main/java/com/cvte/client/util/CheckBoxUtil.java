package com.cvte.client.util;

import java.util.Map;

public class CheckBoxUtil {

	public static void autoConnect(boolean selected) {
		Map<String, String> map = ReadPropertyUtil.readPro();
		if(selected) {
			map.put("Auto_Connection", "1");
		}else {
			map.put("Auto_Connection", "0");
		}
		
		PropertyUtil.writePro(map);
	}

}
