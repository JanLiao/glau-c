package com.cvte.client.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cvte.client.bootstrap.WatchImageService;
import com.cvte.client.constant.Constant;
import com.cvte.client.constant.TableConsts;
import com.cvte.client.gui.Image;
import com.jfoenix.controls.JFXButton;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class TableDataUtil {

	public static void resetData() {
		ObservableList<Image> images = TableConsts.Images;
		if(images != null) {
			images.clear();
			
			//改变table数据
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        PropertyUtil util = new PropertyUtil();
	        if(util.loadProperty()) {
	        	//读取日志CSV文件
	        	List<String[]> list = ReadCSV.readCSV(PropertyUtil.Logger_Path + 
	        			"/" + "logger.csv");
	        	if(new File(PropertyUtil.TransferDirectoryPath).exists()) {       		
	        		File[] files = new File(PropertyUtil.TransferDirectoryPath).listFiles();
	        		Constant.num = files.length;
	        		for(int i = 0; i < files.length; i++) {
	        			HBox box1 = new HBox();
	        			box1.setSpacing(5);
	        			box1.setAlignment(Pos.CENTER);
	        			JFXButton btn11 = new JFXButton("删除");
	        			JFXButton btn12 = new JFXButton("查看报告");
	        			box1.getChildren().add(btn12);
	        			box1.getChildren().add(btn11);
	        			btn11.setStyle("-fx-background-color:RED; -fx-text-fill: WHITE;");
	        			btn12.setStyle("-fx-background-color:GREEN; -fx-text-fill: WHITE;");
	        			String name = files[i].getName();
	        			btn12.setOnAction(new EventHandler<ActionEvent>() {
	        				
	        				@Override
	        				public void handle(ActionEvent e) {
	        					OpenPDF.openPDF(name);
	        				}
	        				
	        			});
	        			String date = "";
	        			try {
	        				date = sdf.format(new Date(files[i].lastModified()));
	        			} catch (Exception e) {
	        				e.printStackTrace();
	        			}
	        			
	        			//检查文件是否已经上传
	        			JFXButton update = new JFXButton("");
	        			//update.setDisable(true);
	        			if(WatchImageService.checkTransfer(files[i], list)) {
	        				update.setText("未上传");
	        				update.setStyle("-fx-background-color:rgb(204, 204, 255); "
	        						+ "-fx-text-fill: WHITE;");
	        			}else {
	        				update.setText("已上传");
	        				update.setStyle("-fx-background-color:rgb(65, 86, 172); "
	        						+ "-fx-text-fill: WHITE;");
	        			}
	        			images.add(new Image("" + (i + 1), files[i].getName(), 
	        					date, update, box1));
	        		}
	        	}
	        }
		}
	}

}
