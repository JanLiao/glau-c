package com.cvte.client.util;

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
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginUtil {
	
	public static final String FX_LABEL_FLOAT_TRUE = "-fx-label-float:true;";
	public static JFXTextField textField;
	public static JFXPasswordField passwordField;
	public static Label loginText;

	public static void startLogin(JFXDialogLayout layout) {
		
	}

	public static void login(Stage stage) {
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
        
        JFXCheckBox jfxCheckBox = new JFXCheckBox("记住密码");
        PropertyUtil prop = new PropertyUtil();
        prop.loadProperty();
        if("1".equals(PropertyUtil.rem)) {
        	textField.setText(PropertyUtil.Account);
        	passwordField.setText("admin123456");
        	jfxCheckBox.setSelected(true);
        }
        jfxCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {  
    		@Override  
    		public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {  
    			PropertyUtil.write(jfxCheckBox);
    		}
    	});
        hbox.getChildren().add(jfxCheckBox);
        content.getChildren().add(hbox);
        
        JFXButton button = new JFXButton("登       录");
        button.getStyleClass().add("button-raised");
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
        		login();
        	}
        });
	}

}
