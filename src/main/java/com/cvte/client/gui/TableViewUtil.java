package com.cvte.client.gui;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cvte.client.bootstrap.WatchImageService;
import com.cvte.client.constant.Constant;
import com.cvte.client.constant.TableConsts;
import com.cvte.client.util.OpenPDF;
import com.cvte.client.util.PropertyUtil;
import com.cvte.client.util.ReadCSV;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TableViewUtil {

	@SuppressWarnings("unchecked")
	public static void createTable(VBox right) {
		JFXTreeTableColumn<Image, String> idColumn = new JFXTreeTableColumn<>("序号");
		Constant.columnList.add(idColumn);
		//idColumn.setPrefWidth(150);
		idColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Image, String> param)
				-> {
            if (idColumn.validateValue(param)) {
                return param.getValue().getValue().id;
            } else {
                return idColumn.getComputedValue(param);
            }
        });
		
		JFXTreeTableColumn<Image, String> imgColumn = new JFXTreeTableColumn<>("图片名");
		Constant.columnList.add(imgColumn);
		//imgColumn.setPrefWidth(170);
		imgColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Image, String> param)
				-> {
            if (imgColumn.validateValue(param)) {
                return param.getValue().getValue().imgName;
            } else {
                return imgColumn.getComputedValue(param);
            }
        });
		
		JFXTreeTableColumn<Image, String> timeColumn = new JFXTreeTableColumn<>("创建时间");
		Constant.columnList.add(timeColumn);
		//timeColumn.setPrefWidth(160);
		timeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Image, String> param)
				-> {
            if (timeColumn.validateValue(param)) {
                return param.getValue().getValue().createTime;
            } else {
                return timeColumn.getComputedValue(param);
            }
        });
		
		JFXTreeTableColumn<Image, String> isColumn = new JFXTreeTableColumn<>("是否上传");
		Constant.columnList.add(isColumn);
		//isColumn.setPrefWidth(140);
		isColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Image, String> param)
				-> {
            if (isColumn.validateValue(param)) {
                return param.getValue().getValue().isUpload;
            } else {
                return isColumn.getComputedValue(param);
            }
        });
		
		JFXTreeTableColumn<Image, Object> uploadColumn = new JFXTreeTableColumn<>("操作");
		Constant.obj = uploadColumn;
		//uploadColumn.setPrefWidth(140);
		uploadColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Image, Object> param)
				-> {
            if (uploadColumn.validateValue(param)) {
                return param.getValue().getValue().upload;
            } else {
                return uploadColumn.getComputedValue(param);
            }
        });
		
		idColumn.setCellFactory((TreeTableColumn<Image, String> param) 
				-> new GenericEditableTreeTableCell<>(
	            new TextFieldEditorBuilder()));
	    idColumn.setOnEditCommit((CellEditEvent<Image, String> t) 
	    		-> t.getTreeTableView().getTreeItem(t.getTreeTablePosition()
	    				.getRow()).getValue().id.set(t.getNewValue()));
	    
	    imgColumn.setCellFactory((TreeTableColumn<Image, String> param) 
				-> new GenericEditableTreeTableCell<>(
	            new TextFieldEditorBuilder()));
	    imgColumn.setOnEditCommit((CellEditEvent<Image, String> t) 
	    		-> t.getTreeTableView().getTreeItem(t.getTreeTablePosition()
	    				.getRow()).getValue().imgName.set(t.getNewValue()));
		
		// data
        ObservableList<Image> images = FXCollections.observableArrayList();
        // 该行以下代码可复用
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PropertyUtil util = new PropertyUtil();
        if(util.loadProperty()) {
        	//读取日志CSV文件
        	List<String[]> list = ReadCSV.readCSV(PropertyUtil.Logger_Path + 
        			"/" + "logger.csv");
        	if(new File(PropertyUtil.TransferDirectoryPath).exists()) {       		
        		File[] files = new File(PropertyUtil.TransferDirectoryPath).listFiles();
        		Constant.num = files.length;
        		for(int i = 0; i < files.length; i++) {
        			HBox box1 = new HBox();
        			box1.setSpacing(5);
        			box1.setAlignment(Pos.CENTER);
        			JFXButton btn11 = new JFXButton("删除");
        			JFXButton btn12 = new JFXButton("查看报告");
        			box1.getChildren().add(btn12);
        			box1.getChildren().add(btn11);
        			btn11.setStyle("-fx-background-color:RED; -fx-text-fill: WHITE;");
        			btn12.setStyle("-fx-background-color:GREEN; -fx-text-fill: WHITE;");
        			String name = files[i].getName();
        			btn12.setOnAction(new EventHandler<ActionEvent>() {
        				
        				@Override
        				public void handle(ActionEvent e) {
        					OpenPDF.openPDF(name);
        				}
        				
        			});
        			String date = "";
        			try {
        				date = sdf.format(new Date(files[i].lastModified()));
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        			
        			//检查文件是否已经上传
        			JFXButton update = new JFXButton("");
        			//update.setDisable(true);
        			if(WatchImageService.checkTransfer(files[i], list)) {
        				update.setText("未上传");
        				update.setStyle("-fx-background-color:rgb(204, 204, 255); "
        						+ "-fx-text-fill: WHITE;");
        			}else {
        				update.setText("已上传");
        				update.setStyle("-fx-background-color:rgb(65, 86, 172); "
        						+ "-fx-text-fill: WHITE;");
        			}
        			images.add(new Image("" + (i + 1), files[i].getName(), 
        					date, update, box1));
        		}
        	}
        }
        TableConsts.Images = images;
        
//        HBox box1 = new HBox();
//        box1.setSpacing(5);
//        box1.setAlignment(Pos.CENTER);
//        JFXButton btn11 = new JFXButton("上传");
//        JFXButton btn12 = new JFXButton("查看报告");
//        box1.getChildren().add(btn11);
//        box1.getChildren().add(btn12);
//        btn11.setStyle("-fx-background-color:rgb(65, 86, 172); -fx-text-fill: WHITE;");
//        btn12.setStyle("-fx-background-color:GREEN; -fx-text-fill: WHITE;");
//        images.add(new Image("1", "23", "CD 1", "是", box1));
        
//        HBox box2 = new HBox();
//        box2.setSpacing(5);
//        box2.setAlignment(Pos.CENTER);
//        JFXButton btn21 = new JFXButton("上传");
//        JFXButton btn22 = new JFXButton("查看报告");
//        box2.getChildren().add(btn21);
//        box2.getChildren().add(btn22);
//        btn21.setStyle("-fx-background-color:rgb(65, 86, 172); -fx-text-fill: WHITE;");
//        btn22.setStyle("-fx-background-color:GREEN; -fx-text-fill: WHITE;");
//        images.add(new Image("2", "22", "Employee 1", "否", box2));
        
//        HBox box3 = new HBox();
//        box3.setSpacing(5);
//        box3.setAlignment(Pos.CENTER);
//        JFXButton btn31 = new JFXButton("上传");
//        JFXButton btn32 = new JFXButton("查看报告");
//        box3.getChildren().add(btn31);
//        box3.getChildren().add(btn32);
//        btn31.setStyle("-fx-background-color:rgb(65, 86, 172); -fx-text-fill: WHITE;");
//        btn32.setStyle("-fx-background-color:GREEN; -fx-text-fill: WHITE;");
//        images.add(new Image("3", "24", "Employee 2", "是", box3));

        // build tree
        final TreeItem<Image> root = new RecursiveTreeItem<>(images, 
        		RecursiveTreeObject::getChildren);

        JFXTreeTableView<Image> treeView = new JFXTreeTableView<>(root);
        treeView.setShowRoot(false);
        treeView.setEditable(true);
        //treeView.getStyleClass().add("edge-to-edge");
        treeView.getStyleClass().add("jan-table-noborder");
        treeView.getColumns().setAll(idColumn, imgColumn, timeColumn, isColumn, uploadColumn);
        Constant.TreeView = treeView;
        
        right.getChildren().add(treeView);
	}

}
