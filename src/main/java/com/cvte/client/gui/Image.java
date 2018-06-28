package com.cvte.client.gui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Image extends RecursiveTreeObject<Image> {

	public final StringProperty id;
	
	public final StringProperty imgName;
	
	public final StringProperty createTime;
	
	public final StringProperty isUpload;
	
	public final ObjectProperty upload;
	
	//public final ObjectProperty report;
	
	public Image(String id, String imgName, String createTime, String isUpload,
			Object upload) {
		this.id = new SimpleStringProperty(id);
		this.imgName = new SimpleStringProperty(imgName);
		this.createTime = new SimpleStringProperty(createTime);
		this.isUpload = new SimpleStringProperty(isUpload);
		this.upload = new SimpleObjectProperty(upload);
		//this.report = new SimpleObjectProperty(report);
	}
}
