package com.cvte.client.util;
/** 
* @author: jan 
* @date: 2018年5月29日 下午7:22:52 
*/

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class MyTask extends Task<Void> {

	private Stage stage;

    public MyTask() {
        setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if (stage != null) {
                	// 两种都OK  个人建议close
                    //stage.hide();
                	stage.close();
                }
            }
        });
    }
    @Override
    protected Void call() throws Exception {
//    	Thread.sleep(10000);
//        for(int i=0;i<10;i++){
//            //@DoSomething()
//            for(long l=0;l<10000;l++);
//            System.out.println("i=" + i);
//        }
        return null;
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
