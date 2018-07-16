package com.cvte.client.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cvte.client.concurrent.PathTask;
import com.cvte.client.constant.Constant;
import com.cvte.client.util.LoginUtil;
import com.cvte.client.util.PropertyUtil;
import com.cvte.client.util.TableDataUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RightBox {

	public static void setBox(StackPane rightDrawerPane, Label label7) {
		JFXScrollPaneMark pane = new JFXScrollPaneMark();
		Label scrollTitle = new Label("眼底筛查demo配置");
		pane.getBottomBar().getChildren().add(scrollTitle);
        scrollTitle.setStyle("-fx-text-fill:WHITE; -fx-font-size: 20;");
        JFXScrollPane.smoothScrolling((ScrollPane) pane.getChildren().get(0));
        StackPane.setMargin(scrollTitle, new Insets(10, 10, 10, 10));
        StackPane.setAlignment(scrollTitle, Pos.CENTER);
		VBox main = new VBox();
		main.setMaxWidth(200);
		main.setSpacing(18);
		main.setAlignment(Pos.CENTER);
		
//		Text scenetitle = new Text("眼底筛查demo配置");
//		scenetitle.setStyle("-fx-font-size: 15px;");
//        //scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
//        main.getChildren().add(scenetitle);
		
		VBox box1 = new VBox();
		Label b1 = new Label("服务器IP");
		TextField ipTextField = new TextField();
		ipTextField.setText(PropertyUtil.ServerIP);
		box1.getChildren().addAll(b1, ipTextField);
		
		VBox box2 = new VBox();
		Label b2 = new Label("服务器端口");
		TextField portText = new TextField();
		portText.setText(PropertyUtil.ServerPORT);
		box2.getChildren().addAll(b2, portText);
		
		VBox box3 = new VBox();
		Label b3 = new Label("服务器tcp端口");
		JFXTextField nettyPortText = new JFXTextField();
		nettyPortText.setText("" + PropertyUtil.ServerNettyPort);
		box3.getChildren().addAll(b3, nettyPortText);
		
		VBox box4 = new VBox();
		Label b4 = new Label("图片路径");
		JFXTextField picText = new JFXTextField();
		picText.setText(PropertyUtil.TransferDirectoryPath);
		box4.getChildren().addAll(b4, picText);
		
		VBox box5 = new VBox();
		Label b5 = new Label("PDF路径");
		JFXTextField pdfText = new JFXTextField();
		pdfText.setText(PropertyUtil.Save_Pdf_Path);
		box5.getChildren().addAll(b5, pdfText);
		
		VBox box6 = new VBox();
		Label b6 = new Label("Logger路径");
		JFXTextField logText = new JFXTextField();
		logText.setText(PropertyUtil.Logger_Path);
		box6.getChildren().addAll(b6, logText);
		
		VBox box7 = new VBox();
		Label b7 = new Label("PDF阅读器路径");
		JFXTextField readText = new JFXTextField();
		readText.setText(PropertyUtil.Pdf_Read_Path);
		box7.getChildren().addAll(b7, readText);
		
		HBox box8 = new HBox();
		box8.setAlignment(Pos.CENTER);
		box8.setSpacing(10);
		JFXButton btn1 = new JFXButton("保存");
        btn1.setStyle("-fx-padding: 0.7em 0.57em;"
        		+ "-fx-font-size: 14px;"
        		+ "-jfx-button-type: RAISED;"
        		+ "-fx-background-color: rgb(77, 102, 204);"
        		+ "-fx-pref-width: 80;-fx-text-fill: WHITE;");
        
        Map<String, String> map = new HashMap<String, String>();
        btn1.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
            	//System.out.println("label = " + label7.getText());
            	//两种方式都OK
            	setLabelText(label7, picText.getText());
            	//setLabelText1(label7, picText.getText());
            	
            	PropertyUtil util = new PropertyUtil();
            	if(util.loadProperty()) {
            		map.put("Serial_Number", PropertyUtil.SerialNumber);
            		map.put("Connect_Password", PropertyUtil.ConnectPassword);
            		map.put("rem", PropertyUtil.rem);
            		map.put("Auto_Connection", PropertyUtil.Auto_Connection);
            		map.put("num", PropertyUtil.num);
            	}
            	//将配置信息存储到map中
            	map.put("Server_IP", ipTextField.getText());
            	map.put("Server_PORT", portText.getText());map.put("Server_Netty_Port", nettyPortText.getText());
            	map.put("Transfer_Directory_Path", picText.getText());map.put("Save_Pdf_Path", pdfText.getText());
            	map.put("Img_Result_Path", "D:/fullCupFile");map.put("Logger_Path", logText.getText());
            	map.put("Pdf_Read_Path", readText.getText());
            	PropertyUtil.writePro(map);
            	
            	// Transfer_Directory_Path改变,table也实时刷新
            	TableDataUtil.resetData();
            }
        });
        
        JFXButton btn2 = new JFXButton("退出");
        btn2.setStyle("-fx-padding: 0.7em 0.57em;"
        		+ "-fx-font-size: 14px;"
        		+ "-jfx-button-type: RAISED;"
        		+ "-fx-background-color: rgb(77, 102, 204);"
        		+ "-fx-pref-width: 80;-fx-text-fill: WHITE;");
        btn2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Constant.btn.setText("未登录");
				Constant.LoginFlag = 0;
				
				//退出保存信息 client.properties
				PropertyUtil util = new PropertyUtil();
            	if(util.loadProperty()) {
            		map.put("Serial_Number", PropertyUtil.SerialNumber);
            		map.put("Connect_Password", PropertyUtil.ConnectPassword);
            		map.put("rem", "0");
            	}
            	//将配置信息存储到map中
            	map.put("Server_IP", ipTextField.getText());
            	map.put("Server_PORT", portText.getText());map.put("Server_Netty_Port", nettyPortText.getText());
            	map.put("Transfer_Directory_Path", picText.getText());map.put("Save_Pdf_Path", pdfText.getText());
            	map.put("Img_Result_Path", "D:/fullCupFile");map.put("Logger_Path", logText.getText());
            	map.put("Pdf_Read_Path", readText.getText());
            	PropertyUtil.writePro(map);
				
				Constant.btn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						Stage newStage = new Stage();
						LoginUtil.login(newStage);
					}
					
				});
			}
        	
        });
        
        box8.getChildren().addAll(btn1, btn2);
        main.getChildren().addAll(box1, box2, box3, box4, box5, box6, box7, box8);
        pane.setContent(main);
        rightDrawerPane.getChildren().add(pane);
	}
	
	public static void setLabelText(Label label, String text) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				System.out.println("开始执行 = " + text + "=" + label.getText());
				Platform.runLater(() -> label.setText(text));
			}
			
		};
		
		ExecutorService service = Executors.newFixedThreadPool(1);
		service.submit(runnable);
	}

	protected static void setLabelText1(Label label, String text) {
		System.out.println("right = " + text);
		PathTask task = new PathTask();
		task.setLabel(label);
		task.setText(text);
		new Thread(task).start();
	}

}
