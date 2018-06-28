package com.cvte.client.gui;

import java.io.File;
import java.io.FileInputStream;

import com.cvte.client.constant.Constant;
import com.cvte.client.util.DialogUtil;
import com.cvte.client.util.LoginUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CVTEGUI extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		AnchorPane pane = new AnchorPane();
		//pane.setStyle("-fx-background-color:WHITE");
//		JFXButton button = new JFXButton("1");
//		button.setPrefSize(1, 1);
		
		VBox left = new VBox();
		left.setSpacing(15);
		left.setPrefWidth(200);
		left.setMaxWidth(200);
		
		HBox box1 = new HBox();
		box1.setPrefWidth(200);
		box1.setMaxWidth(200);
		box1.setAlignment(Pos.CENTER);
		//Label lb = new Label("1");
		JFXButton button1 = new JFXButton("未登录");
		button1.setStyle("-fx-font-size: 15px;");
		button1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				LoginUtil.login(stage);
			}
			
		});
		
//		button1.setStyle(
//				"-fx-padding: 0.7em 0.57em;\r\n" + 
//				"    -fx-font-size: 14px;\r\n");
		//FileInputStream fis = new FileInputStream(new File("setting.png"));
		Image image = new Image(getClass().getResourceAsStream("4.png"));
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setSmooth(true);
		view.setCache(true);
		view.setFitWidth(30);
		view.setFitHeight(30);
		box1.getChildren().addAll(button1, view);
		box1.setSpacing(30);
		left.getChildren().add(box1);
		
		HBox box2 = new HBox();
		box2.setSpacing(10);
		box2.setAlignment(Pos.CENTER_LEFT);
		Label label1 = new Label("状态:  ");
		Label label2 = new Label("未连接");
		label1.setStyle("-fx-font-size: 15px;");
		label2.setStyle("-fx-text-fill:RED;-fx-font-size: 15px;");
		box2.getChildren().addAll(label1, label2);
		left.getChildren().add(box2);
		
		HBox box3 = new HBox();
		box3.setSpacing(15);
		box3.setAlignment(Pos.CENTER_LEFT);
		JFXButton button2 = new JFXButton("连接");
		button2.setStyle("-fx-background-color:GREEN;"
				+ "-fx-text-fill: WHITE;-fx-font-size: 12px;");
		JFXCheckBox checkBox = new JFXCheckBox("自动连接");
		checkBox.setStyle("-fx-font-size:13px;");
		box3.getChildren().addAll(button2, checkBox);
		left.getChildren().add(box3);				
		
		HBox boxbar = new HBox();
		boxbar.setMaxWidth(200);
		boxbar.setPrefWidth(200);
		JFXProgressBar bar = new JFXProgressBar();
        bar.setPrefWidth(200);
        bar.setMaxWidth(200);
        boxbar.getChildren().add(bar);

//        JFXProgressBar jfxBarInf = new JFXProgressBar();
//        jfxBarInf.setPrefWidth(500);
//        jfxBarInf.setPrefHeight(15);
//        jfxBarInf.setProgress(-1.0f);

//        Timeline timeline = new Timeline(
//            new KeyFrame(
//                Duration.ZERO,
//                new KeyValue(jfxBar.secondaryProgressProperty(), 0),
//                new KeyValue(jfxBar.progressProperty(), 0),
//                new KeyValue(jfxBar.secondaryProgressProperty(), 0)),
//            new KeyFrame(
//                Duration.seconds(1),
//                new KeyValue(jfxBar.secondaryProgressProperty(), 1)),
//            new KeyFrame(
//                Duration.seconds(2),
//                new KeyValue(jfxBar.progressProperty(), 1)));
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
        left.getChildren().addAll(boxbar);
        
        HBox box4 = new HBox();
		Label label3 = new Label("连接时长:    ");
		label3.setStyle("-fx-font-size:15px;");
		Label label4 = new Label("----:--:--");
		label4.setStyle("-fx-font-size:15px;");
		box4.getChildren().addAll(label3, label4);
		left.getChildren().add(box4);
        
        Label label5 = new Label("历史连接记录");
        label5.setStyle("-fx-font-size:15px;");
        left.getChildren().add(label5);
        
        JFXListView<Label> list = new JFXListView<>();
        list.setMaxWidth(200);
        list.setPrefWidth(200);
        for (int i = 0; i < 4; i++) {
            list.getItems().add(new Label("ITEM" + i));
        }
        left.getChildren().add(list);
        
        VBox right = new VBox();
        right.setStyle("-fx-background-color:WHITE");
        //right.setSpacing(3);
        
        Label nulllabel1 = new Label("");
        right.getChildren().add(nulllabel1);
        
        HBox box5 = new HBox();
        box5.setSpacing(10);
        Label label6 = new Label("  路径 > ");
        Label label7 = new Label("C:/");
        label6.setStyle("-fx-font-size:15px;");
        label7.setStyle("-fx-font-size:15px;");
        box5.getChildren().addAll(label6, label7);
        right.getChildren().add(box5);
        
        Label nulllabel2 = new Label("");
        right.getChildren().add(nulllabel2);
        
//        JFXListView<Label> javaList = new JFXListView<>();
//        for (int i = 0; i < 4; i++) {
//        	javaList.getItems().add(new Label("ITEM" + i));
//        }
//        right.getChildren().add(javaList);
        
        TableViewUtil.createTable(right);
        pane.getChildren().add(right);
        
        pane.getChildren().add(left);
        AnchorPane.setLeftAnchor(left, 14.0);
        AnchorPane.setTopAnchor(left, 14.0);
        AnchorPane.setBottomAnchor(left, 12.0);
//        AnchorPane.setRightAnchor(left, 465.0);
        
        AnchorPane.setLeftAnchor(right, 228.0);
        AnchorPane.setTopAnchor(right, 0.0);
        AnchorPane.setBottomAnchor(right, 0.0);
        AnchorPane.setRightAnchor(right, 0.0);
        
//        StackPane main = new StackPane();
//        main.getChildren().add(left);
//        main.setStyle("-fx-background-color:WHITE");
        
        Scene scene = new Scene(pane, 1000, 600, Color.WHITE);
        stage.setTitle("眼底筛查 demo");
        scene.getStylesheets().add(CVTEGUI.class.getResource("jan-list.css").toExternalForm());
        stage.setScene(scene);
        
        stage.show();
        pane.requestFocus();
        System.out.println(stage.getWidth() + "=" + stage.getHeight());
        System.out.println(scene.getWidth() + "=" + scene.getHeight());
        double width = scene.getWidth();
        double height = scene.getHeight();
        TableUtil.setTableSize(width, height);
        
        ListenerUtil.stageListen(stage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
