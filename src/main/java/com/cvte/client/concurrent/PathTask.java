package com.cvte.client.concurrent;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class PathTask extends Task<Void> {
	private Label label;
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}
	
	public PathTask() {
		setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("path 修改成功结束");
            }
        });
	}

	@Override
	protected Void call() throws Exception {
		System.out.println(label.getText());
		System.out.println(getText() + "=");
		//Platform.runLater(() -> label.setText(getText()));		
		return null;
	}

}
