package com.cvte.client.gui;

import com.cvte.client.bootstrap.NettyClient;
import com.cvte.client.bootstrap.NettyClientHandler;
import com.cvte.client.constant.Constant;
import com.cvte.client.util.PropertyUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.effects.JFXDepthManager;

import javafx.application.Application;
import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author jan
 * @date 2018年4月6日 上午12:03:08
 * @version V1.0 
 */
public class ClientGUI extends Application {
	
	public static JFXTextArea area = null;
	
	private static final String FX_BACKGROUND_COLOR_WHITE = "-fx-background-color:WHITE;";
	private static final String FX_TEXT_COLOR = "-fx-text-fill: #3CB371; -fx-font-weight: BOLD;-fx-font-size: 15px;";
	//private static final String FX_TEXT_FONT_SIZE = "-fx-font-size: 1em;";

	public void start(Stage stage) {
		
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        //grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setPadding(new Insets(20, 20, 20, 20));

        //Text scenetitle = new Text("Welcome");
        //scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        Label l2 = new Label("眼底筛查 demo 客户端");
        l2.setStyle(FX_BACKGROUND_COLOR_WHITE);
        //l2.getStyleClass().add("button-raised");
        l2.setPadding(new Insets(10, 10, 10, 10));       
        l2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        l2.setTextFill(Color.BLUE);
        JFXRippler rippler2 = new JFXRippler(l2);
        JFXDepthManager.setDepth(rippler2, 5);
        
        //grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(rippler2, 0, 0, 6, 1);
        
      JFXButton button = new JFXButton("");
      button.setMinWidth(1);
      button.setMinHeight(1);
      grid.add(button, 0, 4);
        
        area = new JFXTextArea();
        area.setPromptText("日志输出面板");
        area.setLabelFloat(true);
        area.setMinHeight(300);
        area.setMinWidth(700);
        area.setStyle(FX_TEXT_COLOR);
        //area.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        //area.setStyle(FX_TEXT_FONT_SIZE);
        
        JFXRippler rippler5 = new JFXRippler(area);
        //grid.getChildren().add(rippler5);
        grid.add(rippler5, 2, 3);
        JFXDepthManager.setDepth(rippler5, 8);

        StackPane pane = new StackPane();
        pane.getChildren().add(grid);
        StackPane.setMargin(grid, new Insets(100));
        pane.setStyle("-fx-background-color:WHITE");

        final Scene scene = new Scene(pane, 800, 600);
        scene.getStylesheets().add(ClientGUI.class.getResource("jfoenix-components.css").toExternalForm());
        stage.setTitle("眼  底  demo  系  统");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("3.jpg")));
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
            	System.out.println("end======");
                Constant.flag = 1;
                NettyClientHandler.channelcx.channel().close();
                Constant.flag = 0;
                System.exit(0);
            }
        });
        stage.show();
        
    }

	public static void main(String[] args) {
    	Thread conThread = new Thread(new Runnable() {

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
    	System.out.println("start====");
        launch(args);
        
    }
}
