package com.cvte.client.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/** 
* @author: jan 
* @date: 2018年6月7日 上午11:42:55 
*/
public class DialogUtil {

	@SuppressWarnings("rawtypes")
	public static void showAlert(Stage stage, String msg) {
		JFXAlert alert = new JFXAlert((Stage)stage.getScene().getWindow());
        alert.setOverlayClose(false);
        alert.setSize(320, 160);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("温馨小提示"));
        Label content = new Label(msg);
        layout.setBody(content);
        JFXButton closeButton = new JFXButton("关  闭");
        closeButton.setStyle("-fx-background-color: GREEN;-fx-text-fill: WHITE;-fx-font-size: 15px;-fx-padding: 0.5em 0.50em;");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
	}		
	
	private static String openDataDir() {
		String imgPath = "";
		Stage fileStage = null;
	    DirectoryChooser folderChooser = new DirectoryChooser();
	    folderChooser.setTitle("标注图片文件夹");
	    File selectedFile = folderChooser.showDialog(fileStage);
	    if(selectedFile == null) {
	    	System.out.println("未选择文件夹");
	    }
	    else {
	    	String dir = selectedFile.getAbsolutePath().replace('\\', '/');
		    System.out.println("selected data dir = " + dir);
		    imgPath = dir;
	    }
		return imgPath;
	}

	public static String selectPath() {
		String imgpath = "";
		Stage fileStage = null;
	    DirectoryChooser folderChooser = new DirectoryChooser();
	    folderChooser.setTitle("标注图片文件夹");
	    File selectedFile = folderChooser.showDialog(fileStage);
	    if(selectedFile == null) {
	    	System.out.println("未选择文件夹");
	    }
	    else {
	    	String dir = selectedFile.getAbsolutePath().replace('\\', '/');
		    System.out.println("selected data dir = " + dir);
		    imgpath = dir;
	    }
	    return imgpath;
	}
	
	private static boolean checkDeleted(String imgName, List<String[]> dirList) {
		boolean flag = true;
		for(int i = 0; i < dirList.size(); i++) {
			if(imgName.split(",")[0].equals(dirList.get(i)[1])) {
				if(Integer.parseInt(dirList.get(i)[5]) == 0) {
					flag = false;
					break;
				}
				else {
					break;
				}
			}
		}
		return flag;
	}

	public static void login(Stage stage) {
		JFXAlert alert = new JFXAlert((Stage)stage.getScene().getWindow());
        alert.setOverlayClose(false);
        alert.setSize(320, 160);
        JFXDialogLayout layout = new JFXDialogLayout();
        //layout.setHeading(new Label("登录"));
        VBox content = new VBox();
        content.setSpacing(30);
        content.setStyle("-fx-background-color:WHITE;-fx-padding:80;");
        content.setPrefWidth(420);
        content.setMinHeight(360);
        
        LoginUtil.startLogin(layout);
        
        layout.setBody(content);
        JFXButton closeButton = new JFXButton("关  闭");
        closeButton.setStyle("-fx-background-color: GREEN;-fx-text-fill: WHITE;-fx-font-size: 15px;-fx-padding: 0.5em 0.50em;");
        closeButton.setOnAction(event -> {
        	alert.hideWithAnimation();
        });
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
	}
	
}
