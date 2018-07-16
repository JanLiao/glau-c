package com.cvte.client.concurrent;

import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class MyTask extends Task<Void> {
	private Label label;
	private boolean connect;
	private long start;

	public boolean isConnect() {
		return connect;
	}

	public void setConnect(boolean connect) {
		this.connect = connect;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}
	
	public MyTask() {
		setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("成功结束");
            }
        });
	}

	@Override
	protected Void call() throws Exception {
		DecimalFormat dfh = new DecimalFormat("0000");
		DecimalFormat dfm = new DecimalFormat("00");
		DecimalFormat dfs = new DecimalFormat("00");
		while(connect) {
			Thread.sleep(1000);
			long end = System.currentTimeMillis();
			long inter = (end - start)/1000;
			int hh = (int) (inter/3600);
			int mm = (int) ((inter%3600)/60);
			int ss = (int) (inter%60);
			System.out.println(dfh.format(hh) + ":" + dfm.format(mm) + ":" + dfs.format(ss));
			
			//开始计时  两种方式都OK
			//Platform.runLater(() -> label.textProperty().set(dfh.format(hh) + ":" + dfm.format(mm) + ":" + dfs.format(ss)));
			Platform.runLater(() -> label.setText(dfh.format(hh) + ":" + dfm.format(mm) + ":" + dfs.format(ss)));
			//System.out.println(label.getText() + "=" + getLabel().getText());
			
			//该种方式有误
			//getLabel().setText("kk = " + inter);
		}
		return null;
	}

}
