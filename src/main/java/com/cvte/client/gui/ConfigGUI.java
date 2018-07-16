package com.cvte.client.gui;

import java.util.HashMap;
import java.util.Map;

import com.cvte.client.util.PropertyUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConfigGUI extends Application{
	
	public static Map<String, String> map = new HashMap<String, String>();
	
	//private static final String FX_BACKGROUND_COLOR_WHITE = "-fx-background-color:WHITE;";

	@Override
	public void start(Stage stage) throws Exception {
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        //grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setPadding(new Insets(20, 20, 20, 20));

        Text scenetitle = new Text("眼底筛查demo配置");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
//        Label l2 = new Label("Welcome");
//        l2.setStyle(FX_BACKGROUND_COLOR_WHITE);
//        l2.setPadding(new Insets(10, 10, 10, 10));       
//        JFXRippler rippler2 = new JFXRippler(l2);
//        JFXDepthManager.setDepth(rippler2, 2);
        
        grid.add(scenetitle, 0, 0, 2, 1);
        //grid.add(rippler2, 0, 0, 2, 3);

        //服务器IP
        Label ip = new Label("服务器IP:");
        grid.add(ip, 0, 2);
        TextField ipTextField = new TextField();
        ipTextField.setText(PropertyUtil.ServerIP);
        grid.add(ipTextField, 1, 2);

        //服务器端口
        Label port = new Label("服务器端口:");
        grid.add(port, 0, 3);
        TextField portText = new TextField();
        portText.setText(PropertyUtil.ServerPORT);
        grid.add(portText, 1, 3);
        
        //服务器netty端口(tcp端口)
        Label nettyPort = new Label("服务器tcp端口:");
        grid.add(nettyPort, 0, 4);
        JFXTextField nettyPortText = new JFXTextField();
        nettyPortText.setLabelFloat(true);
        nettyPortText.setPromptText("Input Server Netty Port");
        nettyPortText.setText("" + PropertyUtil.ServerNettyPort);
        grid.add(nettyPortText, 1, 4);
        
        //终端ID
        Label terminal = new Label("终端ID:");
        grid.add(terminal, 0, 5);
        JFXTextField terminalText = new JFXTextField();
        terminalText.setLabelFloat(true);
        terminalText.setPromptText("Input terminalNumber");
        terminalText.setText(PropertyUtil.SerialNumber);
        grid.add(terminalText, 1, 5);
        
        //连接密码
        Label pwd = new Label("密码:");
        grid.add(pwd, 0, 6);
        JFXTextField pwdText = new JFXTextField();
        pwdText.setLabelFloat(true);
        pwdText.setPromptText("Input pwd");
        pwdText.setText(PropertyUtil.ConnectPassword);
        grid.add(pwdText, 1, 6);
        
        //相机图片存储路径
        Label pic = new Label("图片路径:");
        grid.add(pic, 0, 7);
        JFXTextField picText = new JFXTextField();
        //picText.setLabelFloat(true);
        picText.setPromptText("Input Picture Path");
        picText.setText(PropertyUtil.TransferDirectoryPath);
        grid.add(picText, 1, 7);
        
        //PDF存储路径
        Label pdf = new Label("PDF路径:");
        grid.add(pdf, 0, 8);
        JFXTextField pdfText = new JFXTextField();
        //pdfText.setLabelFloat(true);
        pdfText.setPromptText("Input PDF Path");
        pdfText.setText(PropertyUtil.Save_Pdf_Path);
        grid.add(pdfText, 1, 8);
        
        //log 存储路径
        Label log = new Label("Logger路径:");
        grid.add(log, 0, 9);
        JFXTextField logText = new JFXTextField();
        //logText.setLabelFloat(true);
        logText.setPromptText("Input log Path");
        logText.setText(PropertyUtil.Logger_Path);
        grid.add(logText, 1, 9);
        
        Label read = new Label("PDF阅读器路径:");
        grid.add(read, 0, 10);
        JFXTextField readText = new JFXTextField();
        //readText.setLabelFloat(true);
        readText.setPromptText("Input Pdf Open Path");
        readText.setText(PropertyUtil.Pdf_Read_Path);
        readText.setMinWidth(200);
        grid.add(readText, 1, 10);
        
        JFXButton btn = new JFXButton("保存");
        btn.getStyleClass().add("button-raised");
        grid.add(btn, 1, 11);

//        Button btn = new Button("保存");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(btn);
//        grid.add(hbBtn, 1, 11);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 12);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
            	//将配置信息存储到map中
            	map.put("Server_IP", ipTextField.getText());
            	map.put("Server_PORT", portText.getText());map.put("Server_Netty_Port", nettyPortText.getText());
            	map.put("Serial_Number", terminalText.getText());map.put("Connect_Password", pwdText.getText());
            	map.put("Transfer_Directory_Path", picText.getText());map.put("Save_Pdf_Path", pdfText.getText());
            	map.put("Img_Result_Path", "D:/fullCupFile");map.put("Logger_Path", logText.getText());
            	map.put("Pdf_Read_Path", readText.getText());
                //actiontarget.setFill(Color.FIREBRICK);
            	actiontarget.setFill(Color.GREEN);
            	PropertyUtil.writePro(map);
                actiontarget.setText("配置信息保存成功");
            }
        });


        StackPane pane = new StackPane();
        pane.getChildren().add(grid);
        StackPane.setMargin(grid, new Insets(100));
        pane.setStyle("-fx-background-color:WHITE");

        final Scene scene = new Scene(pane, 550, 600);
        scene.getStylesheets().add(ConfigGUI.class.getResource("jfoenix-components.css").toExternalForm());
        stage.setTitle("眼  底  demo  系  统");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("3.jpg")));
        stage.setScene(scene);
        stage.show();
	}

	public static void main(String[] args) {
		PropertyUtil propertyUtil = new PropertyUtil();
		if(propertyUtil.loadProperty()) {
			launch(args);
		}
	}
	
}
