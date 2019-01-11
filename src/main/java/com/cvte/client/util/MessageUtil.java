package com.cvte.client.util;

import com.cvte.client.constant.Constant;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
* @author jan
* @data 2018年10月18日 下午3:32:20
*/
public class MessageUtil {

	public static void messageShow() {
		HBox box1 = new HBox();
		HBox box2 = new HBox();
		Label label1 = new Label("消息提示开启...");
		label1.setStyle("-fx-text-fill: rgb(119, 25, 170);-fx-font-size: 15px;");
		Constant.M1 = null;
		Constant.M1 = label1;
		Label label2 = new Label("消息提示开启...");
		label2.setStyle("-fx-text-fill: rgb(119, 25, 170);-fx-font-size: 15px;");
		Constant.M2 = null;
		Constant.M2 = label2;
		box1.setAlignment(Pos.CENTER_LEFT);
		box1.getChildren().add(label1);
		box2.setAlignment(Pos.CENTER_RIGHT);
		box2.getChildren().add(label2);
		VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setBackground(Background.EMPTY);
        //vBox.setStyle("-fx-opacity: 0.4;");
        vBox.getChildren().addAll(box1,box2);
        Stage dialogStage = new Stage();
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        //dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setAlwaysOnTop(true);
        Scene scene = new Scene(vBox, 600, 80);
        scene.setFill(null);
        dialogStage.setScene(scene);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();//获取屏幕宽度高度
        double width = primaryScreenBounds.getWidth();//屏幕宽度
        double height = primaryScreenBounds.getHeight();//屏幕高度
        double width1 = Constant.STAGE.getWidth();
        double height1 = Constant.STAGE.getHeight();
        System.out.println(width + "=" + height + "=" + width1 + "=" + height1);
        dialogStage.setX((width - 600)/2);
        dialogStage.setY((height + height1)/2 - 100);
        Constant.MESSAGE = dialogStage;
        dialogStage.show();
	}
	
	public static void updateMessage(String s) {
		System.out.println("message = " + s);
		if(Constant.MESSAGE != null) {
			Platform.runLater(() -> {
				Constant.M1.setText(Constant.M2.getText());
				Constant.M2.setText(s);
			});
		}
	}
}
