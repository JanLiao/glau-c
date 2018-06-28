package com.cvte.client.gui;

import java.util.Timer;
import java.util.TimerTask;

import com.cvte.client.constant.Constant;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

public class ListenerUtil {

	public static void stageListen(Stage stage) {
		final ChangeListener<Number> listener = new ChangeListener<Number>() {

			final Timer timer = new Timer();
			TimerTask task = null;
			final long delayTime = 10;
			
			@Override
			public void changed(ObservableValue<? extends Number> observable, 
					Number oldValue, Number newValue) {
				if(task != null) {
					task.cancel();
				}
				
				task = new TimerTask() {

					@Override
					public void run() {
						//System.out.println(stage.getWidth() + "=" + stage.getHeight());
						System.out.println(stage.getScene().getWidth() + "="
						           + stage.getScene().getHeight());
						TableUtil.setTableSize(stage.getScene().getWidth(), 
								stage.getScene().getHeight());
					}
					
				};
				
				timer.schedule(task, delayTime);
			}
			
		};
		
		stage.widthProperty().addListener(listener);
		stage.heightProperty().addListener(listener);
	}

}
