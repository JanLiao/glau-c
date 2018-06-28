package com.cvte.client.gui;

import java.io.File;
import java.io.FileInputStream;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		VBox left = new VBox();
		HBox box1 = new HBox();
		//box1.setAlignment(Pos.CENTER);
		JFXButton button1 = new JFXButton("未登录");
		button1.setStyle(
				"-fx-padding: 0.7em 0.57em;\r\n" + 
				"    -fx-font-size: 14px;\r\n" 
//				"    -jfx-button-type: RAISED;\r\n"
//				"    -fx-background-color: rgb(77,102,204);\r\n" + 
//				"    -fx-pref-width: 200;\r\n" + 
//				"    -fx-text-fill: WHITE;"
				);
		//FileInputStream fis = new FileInputStream(new File("F:\\eclipse-workspace-new1\\GlaucomaClient\\src\\main\\java\\com\\cvte\\client\\gui\\setting.png"));
		//FileInputStream fis = new FileInputStream(new File("./1.jpg"));
		Image image = new Image(getClass().getResourceAsStream("setting.png"));
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setSmooth(true);
		view.setCache(true);
		view.setFitWidth(50);
		view.setFitHeight(50);
		box1.getChildren().addAll(button1, view);
		box1.setSpacing(5);		
		left.getChildren().add(box1);
		
		StackPane main = new StackPane();
        main.getChildren().add(left);
        main.setStyle("-fx-background-color:WHITE");		
		
		Scene scene = new Scene(main, 1000, 600, Color.WHITE);
        stage.setTitle("JFX ListView Demo ");
        //scene.getStylesheets().add(CVTEGUI.class.getResource("jfoenix-components.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
