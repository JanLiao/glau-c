package com.cvte.client.gui;

import com.cvte.client.constant.Constant;

public class TableUtil {

	public static void setTableSize(double width, double height) {
		Constant.columnList.get(0).setPrefWidth((width - 228)*0.13);
        Constant.columnList.get(1).setPrefWidth((width - 228)*0.30);
        Constant.columnList.get(2).setPrefWidth((width - 228)*0.20);
        Constant.columnList.get(3).setPrefWidth((width - 228)*0.12);
        Constant.obj.setPrefWidth((width - 228)*0.25);
        Constant.TreeView.setPrefHeight((height - 15));
	}

}
