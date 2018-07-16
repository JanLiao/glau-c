package com.cvte.client.gui;

import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cvte.client.bootstrap.NettyClient;
import com.cvte.client.bootstrap.NettyClientHandler;
import com.cvte.client.concurrent.MyTask;
import com.cvte.client.constant.Constant;
import com.cvte.client.util.CheckBoxUtil;
import com.cvte.client.util.DialogUtil;
import com.cvte.client.util.LoginUtil;
import com.cvte.client.util.PathInit;
import com.cvte.client.util.PropertyUtil;
import com.cvte.client.util.ReadPropertyUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class CVTEGUI extends Application {

	public Label label4 = null;  //计时label
	public Label label7 = new Label("");  //TransferDirectoryPath 
	public Label label9 = new Label(""); // 用户ID
	Label label10 = new Label("  未生成");
	public Thread conThread = null;
	public ScheduledExecutorService service = null;
	
	@Override
	public void start(Stage stage) throws Exception {
		JFXDrawersStack drawersStack = new JFXDrawersStack();
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
		
		JFXButton button1 = new JFXButton();
		PropertyUtil propertyUtil = new PropertyUtil();
		if (propertyUtil.loadProperty()) {
			if("0".equals(PropertyUtil.rem)) {
				button1.setText("未登录");
//				EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
//
//					@Override
//					public void handle(ActionEvent e) {
//						Stage newStage = new Stage();
//						LoginUtil.login(newStage);
//					}
//					
//				};
				button1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent e) {
						Stage newStage = new Stage();
						LoginUtil.login(newStage);
					}
					
				});
			}
			else if("1".equals(PropertyUtil.rem)) {
				Constant.LoginFlag = 1;
				button1.setText("终端:" + PropertyUtil.SerialNumber);
			}
		}
		
		
		Constant.btn = button1;
		button1.setStyle("-fx-font-size: 15px;");		
		
//		button1.setStyle(
//				"-fx-padding: 0.7em 0.57em;\r\n" + 
//				"    -fx-font-size: 14px;\r\n");
		//FileInputStream fis = new FileInputStream(new File("setting.png"));
		
		//Drawer right
		JFXDrawer rightDrawer = new JFXDrawer();
        StackPane rightDrawerPane = new StackPane();
        rightDrawerPane.getStyleClass().add("white-A100");
        RightBox.setBox(rightDrawerPane, label7);
        rightDrawer.setDirection(DrawerDirection.RIGHT);
        rightDrawer.setDefaultDrawerSize(250);
        rightDrawer.setSidePane(rightDrawerPane);
        rightDrawer.setOverLayVisible(false);
        rightDrawer.setResizableOnDrag(true);
		       
		Image image = new Image(getClass().getResourceAsStream("4.png"));
		ImageView view = new ImageView(image);
		view.setPreserveRatio(true);
		view.setSmooth(true);
		view.setCache(true);
		view.setFitWidth(30);
		view.setFitHeight(30);		
		HBox tmpbox = new HBox();
		tmpbox.setMaxSize(30, 30);
		tmpbox.getChildren().add(view);
//		drawersStack.addEventHandler(MOUSE_PRESSED, e -> drawersStack.toggle(rightDrawer));
		tmpbox.addEventHandler(MOUSE_PRESSED, e -> drawersStack.toggle(rightDrawer));
//		tmpbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent arg0) {
//				System.out.println(8888);
//			}
//			
//		});
		box1.getChildren().addAll(button1, tmpbox);
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
		box3.setSpacing(25);
		box3.setAlignment(Pos.CENTER_LEFT);
		JFXButton button2 = new JFXButton("连接");		
		button2.setStyle("-fx-background-color:GREEN;"
				+ "-fx-text-fill: WHITE;-fx-font-size: 12px;");
		button2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				
				if(Constant.ConnectionFlag == 0) {
					connectBtn(label2, button2);
				}else if(Constant.ConnectionFlag == 1) {
					disconnectBtn(label2, button2);
				}
				
			}
			
		});
		
		JFXCheckBox checkBox = new JFXCheckBox("自动连接");
		checkBox.setStyle("-fx-font-size:13px;");
		checkBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				CheckBoxUtil.autoConnect(checkBox.isSelected());
			}
			
		});		
		box3.getChildren().addAll(button2, checkBox);
		left.getChildren().add(box3);				
		
		HBox boxbar = new HBox();
		boxbar.setMaxWidth(200);
		boxbar.setPrefWidth(200);
		JFXProgressBar bar = new JFXProgressBar();
        bar.setPrefWidth(200);
        bar.setMaxWidth(200);
        Tooltip tip = new Tooltip("未连接");
        bar.setTooltip(tip);
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
		label4 = new Label("----:--:--");
		label4.setStyle("-fx-font-size:15px;");
		box4.getChildren().addAll(label3, label4);
		left.getChildren().add(box4);
        
        Label label5 = new Label("今日连接记录");
        label5.setStyle("-fx-font-size:15px;");
        left.getChildren().add(label5);
        
        JFXListView<Label> list = new JFXListView<>();
        list.setMaxWidth(200);
        list.setPrefWidth(200);
        list.setPrefHeight(200);
        for (int i = 0; i < 4; i++) {
            list.getItems().add(new Label("18-07-03 12:22:22 0000:00:00"));
        }
        left.getChildren().add(list);
        
        left.getChildren().add(new Label(""));
        
        HBox box6 = new HBox();
        box6.setSpacing(20);
        JFXButton button3 = new JFXButton("顺序生成用户名");
		button3.setStyle("-fx-background-color:rgb(114, 114, 255);"
				+ "-fx-text-fill: WHITE;-fx-font-size: 15px;");
		button3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				PropertyUtil prop1 = new PropertyUtil();
				if(prop1.loadProperty()) {
					label10.setText("");
					label9.setText(new DecimalFormat("00000")
							.format(Integer.parseInt(PropertyUtil.num)));
					DialogUtil.generateNum(stage, PropertyUtil.num);
					Map<String, String> map = ReadPropertyUtil.readPro();
					map.put("num", "" + (Integer.parseInt(PropertyUtil.num) + 1));
					PropertyUtil.writePro(map);
				}
			}
			
		});
		JFXButton button4 = new JFXButton("重置");
		button4.setStyle("-fx-background-color:RED;"
				+ "-fx-text-fill: WHITE;-fx-font-size: 15px;");
		button4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				DialogUtil.resetNum(stage);
			}
			
		});
		box6.getChildren().addAll(button3, button4);
		left.getChildren().add(box6);
		
		VBox box9 = new VBox();
		HBox box7 = new HBox();
		box7.setSpacing(10);
		Label label8 = new Label("当前用户ID:");
		label8.setStyle("-fx-font-size:16px;-fx-text-fill: SeaGreen;");		
		label10.setStyle("-fx-font-size:16px;-fx-text-fill: LimeGreen;");
		box7.getChildren().addAll(label8, label10);
		HBox box8 = new HBox();
		box8.setAlignment(Pos.CENTER);
		box8.getChildren().add(label9);
		label9.setStyle("-fx-font-size:25px;-fx-text-fill: IndianRed;");
		box9.getChildren().addAll(new Label(""), box7, box8);
		left.getChildren().add(box9);
        
        //设置自动连接服务器
        PropertyUtil prop = new PropertyUtil();
        if(prop.loadProperty()) {
        	if("1".equals(PropertyUtil.Auto_Connection)) {
        		checkBox.setSelected(true);
        		connectBtn(label2, button2);
        	}
        }
        
        VBox right = new VBox();
        right.setStyle("-fx-background-color:WHITE");
        //right.setSpacing(3);
        
        Label nulllabel1 = new Label("");
        right.getChildren().add(nulllabel1);        
        
        HBox box5 = new HBox();
        box5.setSpacing(10);
        Label label6 = new Label("  路径 > ");
        label6.setStyle("-fx-font-size:15px;");
        label7.setText(PropertyUtil.TransferDirectoryPath);
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
        
        TableViewUtil.createTable(right);   //右侧table数据
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
        
        drawersStack.setContent(pane);
        Scene scene = new Scene(drawersStack, 1000, 600, Color.WHITE);
        stage.setTitle("眼底筛查 demo");
        scene.getStylesheets().add(CVTEGUI.class.getResource("jan-list.css").toExternalForm());
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
            	System.out.println("end======");
                //NettyClientHandler.channelcx.channel().close();
                Constant.flag = 0;
                System.exit(0);
            }
        });
        stage.requestFocus();
        
        // 初始化path路径及logger.csv文件
        PathInit.pathInit();
        
        stage.show();
        //pane.requestFocus();
        System.out.println(stage.getWidth() + "=" + stage.getHeight());
        System.out.println(scene.getWidth() + "=" + scene.getHeight());
        double width = scene.getWidth();
        double height = scene.getHeight();
        TableUtil.setTableSize(width, height);
        
        ListenerUtil.stageListen(stage);
	}
	
	//netty客户端主动断开连接
	protected void disconnectBtn(Label label2, JFXButton button2) {
		Constant.ConnectionFlag = 0;
		label2.setText("未连接");
		label2.setStyle("-fx-text-fill:RED;-fx-font-size: 15px;");
		button2.setText("连接");
		button2.setStyle("-fx-background-color:GREEN;-fx-text-fill:WHITE;");
		Constant.flag = 1;  //主动关闭连接（netty）
		Constant.ctx.close();
		conThread.interrupt();
		service.shutdown();
		service.shutdownNow();
		
//		while(service.isShutdown()) {
//			try {
//				service.awaitTermination(1, TimeUnit.SECONDS);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		
		//停止计时
		label4.setText("----:--:--");
		//stopCalculateTime(label4);
		
	}

	//netty连接服务器
	protected void connectBtn(Label label2, JFXButton button2) {
		Constant.ConnectionFlag = 1;
		label2.setText("已连接");
		label2.setStyle("-fx-text-fill:GREEN;-fx-font-size: 15px;");
		button2.setText("断开");
		button2.setStyle("-fx-background-color:RED;-fx-text-fill:WHITE;");
		PropertyUtil propertyUtil = new PropertyUtil();
		if (propertyUtil.loadProperty()) {
			conThread = new Thread(new Runnable() {

				public void run() {
					PropertyUtil propertyUtil = new PropertyUtil();
					if (propertyUtil.loadProperty()) {
						NettyClient nettyClient = new NettyClient();
						try {
							nettyClient.connect(PropertyUtil.ServerNettyPort, PropertyUtil.ServerIP);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
	    		
	    	});
	    	conThread.start();
		}
		
		//连接计时  两种方式都OK
		calculateTime1(label4, System.currentTimeMillis());
		//calculateTime2(label4, System.currentTimeMillis());
	}

	public void calculateTime(long start) {
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				for(int i = 0; i < 1000; i++) {
					//Thread.sleep(1000);
					if(i % 2 == 0) {						
						Platform.runLater(() -> label4.textProperty().set("Count 6"));
					}else {
						Platform.runLater(() -> label4.textProperty().set("Count 7"));
					}
				}
				return null;
			}
			
		};
		
		new Thread(task).start();
	}
	
	//该方法直接导致UI卡死
	public void calculateTime3(Label label, long start) {
		DecimalFormat dfh = new DecimalFormat("0000");
		DecimalFormat dfm = new DecimalFormat("00");
		DecimalFormat dfs = new DecimalFormat("00");
		Platform.runLater(new Runnable() {
            @Override 
            public void run() {
            	while(true) {
            		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        			long end = System.currentTimeMillis();
        			long inter = (end - start)/1000;
        			int hh = (int) (inter/3600);
        			int mm = (int) ((inter%3600)/60);
        			int ss = (int) (inter%60);
        			System.out.println(dfh.format(hh) + ":" + 
        			dfm.format(mm) + ":" + dfs.format(ss));
        			label.setText(dfh.format(hh) + ":" + dfm.format(mm) + ":" + dfs.format(ss));
            	}
            }
        });
	}
	
	public void calculateTime2(Label label, long start) {
		System.out.println(label.getText());
		MyTask task = new MyTask();
		task.setLabel(label4);
		task.setConnect(true);
		task.setStart(start);
		Thread t = new Thread(task);
		t.start();
	}
	
	public void calculateTime1(Label label, long start) {
		DecimalFormat dfh = new DecimalFormat("0000");
		DecimalFormat dfm = new DecimalFormat("00");
		DecimalFormat dfs = new DecimalFormat("00");
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				long end = System.currentTimeMillis();
				long inter = (end - start)/1000;
				int hh = (int) (inter/3600);
				int mm = (int) ((inter%3600)/60);
				int ss = (int) (inter%60);
				//label.setText(df.format(hh) + ":" + mm + ":" + ss);
				Platform.runLater(() -> label.setText(dfh.format(hh) + ":" + dfm.format(mm) + ":" + dfs.format(ss)));
			}
			
		};
		
		service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
