package com.cvte.client.gui;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BtnTest extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		StackPane pane = new StackPane();
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		Button b = new Button();
		JFXButton btn = new JFXButton();
		btn.setMaxSize(0, 0);
		JFXButton btn1 = new JFXButton();
		box.getChildren().add(b);
		box.getChildren().add(btn);
		box.getChildren().add(btn1);
		pane.getChildren().add(box);
		Scene scene = new Scene(pane, 600, 400);
		stage.setScene(scene);
		stage.show();
		pane.requestFocus();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
