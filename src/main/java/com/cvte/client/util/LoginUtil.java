package com.cvte.client.util;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.swing.event.DocumentEvent.EventType;

import com.cvte.client.bootstrap.NettyClient;
import com.cvte.client.constant.Constant;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginUtil {
	
	public static final String FX_LABEL_FLOAT_TRUE = "-fx-label-float:true;";
	public static JFXTextField textField;
	public static JFXPasswordField passwordField;
	public static Label loginText;
	public static Stage loginStage;

	public static void startLogin(JFXDialogLayout layout) {
		
	}

	public static void login(Stage stage) {
		loginStage = stage;
		// 内容
        VBox content = new VBox();
        content.setSpacing(30);
        content.setStyle("-fx-background-color:WHITE;-fx-padding:80;");
        content.setPrefWidth(420);
        content.setMinHeight(360);
        
        textField = new JFXTextField();
        textField.setPromptText("用  户  名");
        content.getChildren().add(textField);
        
        passwordField = new JFXPasswordField();
        passwordField.setStyle(FX_LABEL_FLOAT_TRUE);
        passwordField.setPromptText("密  码");
        content.getChildren().add(passwordField);
                
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);
        loginText = new Label("");
        loginText.setTextFill(Color.RED);
        //loginText.setTextAlignment(TextAlignment.RIGHT);
        loginText.setFont(new Font(16));
        hbox.getChildren().add(loginText);
        
        PropertyUtil prop = new PropertyUtil();
        prop.loadProperty();
        if("1".equals(PropertyUtil.rem)) {
        	textField.setText(PropertyUtil.SerialNumber);
        	passwordField.setText(PropertyUtil.ConnectPassword);
        }
        content.getChildren().add(hbox);
        
        JFXButton button = new JFXButton("登       录");
        button.setStyle("-fx-padding: 0.7em 0.57em;" + 
        		"    -fx-font-size: 16px;" + 
        		"    -jfx-button-type: RAISED;" + 
        		"    -fx-background-color: rgb(77,102,204);" + 
        		"    -fx-pref-width: 260;" + 
        		"    -fx-pref-height:42;" + 
        		"    -fx-text-fill: WHITE;");
        content.getChildren().add(button);
        
        button.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if(e.getCode() == KeyCode.ENTER) {
        			login();
        		}
        	}
        });
        
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if(e.getCode() == KeyCode.ENTER) {
        			login();
        		}
        	}
        });
        
        passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	public void handle(KeyEvent e) {
        		if(e.getCode() == KeyCode.ENTER) {
        			login();
        		}
        	}
        });
        
        button.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		System.out.println("登录");
        		login();
        	}
        });
        
        Scene scene = new Scene(content, 380, 330);
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent event) {
//            	System.out.println("end login======");
//            	oldStage.requestFocus();
//            }
//        });
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
	}

	public static void login() {
		if("".equals(textField.getText()) || "".equals(passwordField.getText())) {
			loginText.setText("账号或密码不能为空");
		}else {
			String account = textField.getText();
			String password = passwordField.getText();
			PropertyUtil propertyUtil = new PropertyUtil();
			if (propertyUtil.loadProperty()) {
				String path = "http://" + PropertyUtil.ServerIP + ":" + 
			PropertyUtil.ServerPORT +  "/Glaucoma/terminalVal.do?account=" + account
			+ "&&password=" + password;
				
				try {
					URL url = new URL(path);
					HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
					int responseCode = httpConn.getResponseCode();
					
					if (responseCode == HttpURLConnection.HTTP_OK) {
						String msg = "";
						String disposition = httpConn.getHeaderField("Content-Disposition");
						int index = disposition.indexOf("msg=");
						if (index > 0) {
							msg = disposition.substring(index + 4, disposition.length());
						}
						System.out.println("msg - utf-8 = " + new String(msg.getBytes("ISO-8859-1"),"UTF-8"));
						String result = new String(msg.getBytes("ISO-8859-1"),"UTF-8");
						
						String[] str = result.split(",");
						if("0".equals(str[0])) {
							loginText.setText(str[1]);
						}
						else if("1".equals(str[0])) {
							Constant.btn.setText("终端:" + account);
							Constant.LoginFlag = 1;
							Constant.btn.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									System.out.println("nothing");
								}
								
							});
							Map<String, String> map = ReadPropertyUtil.readPro();
							map.put("Serial_Number", account);
							map.put("Connect_Password", password);
							map.put("rem", "1");
							PropertyUtil.writePro(map);
							loginStage.close();
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
