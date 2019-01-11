package com.cvte.client.bootstrap;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateMidnight.Property;

import com.cvte.client.constant.Constant;
import com.cvte.client.gui.ClientGUI;
import com.cvte.client.util.DialogUtil;
import com.cvte.client.util.FileUtil;
import com.cvte.client.util.MessageUtil;
import com.cvte.client.util.PropertyUtil;
import com.cvte.client.util.ReadCSV;
import com.cvte.client.util.TableDataUtil;
import com.cvte.netty.msg.MsgType;
import com.cvte.netty.msg.TransferMsg;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author jan
 * @date 2018年3月19日 上午1:48:40
 * @version V1.0 
 */
public class WatchImageService {
	
	public static Lock lock = new ReentrantLock();
	
	@SuppressWarnings("unchecked")
	public static void process(ChannelHandlerContext ctx) {
		File ff = new File(PropertyUtil.Logger_Path);
		if(!ff.exists()) {
			  ff.mkdirs();
		  }
		
		File ff1 = new File(PropertyUtil.Logger_Path + "/" + "logger.csv");
		if(!ff1.exists()) {
			try {
				ff1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//读取Logger file文件夹下csv日志文件
		  List<String[]> list = ReadCSV.readCSV(PropertyUtil.Logger_Path + "/" + "logger.csv");
		  int logSize = list.size();
		  
		  
		  //检测文件是否有未处理的图片
		  File f = new File(PropertyUtil.TransferDirectoryPath);

		  File[] allfile = f.listFiles();
		  if(logSize == 0 && allfile.length == 0) {
			  System.out.println("启动发现当前文件夹为空----");
//			  if(ClientGUI.area != null) {
//				  ClientGUI.area.appendText("启动发现当前文件夹为空----");
//			  }
//			  if(Constant.MESSAGE != null) {
//					Constant.M1.setText(Constant.M2.getText());
//					Constant.M2.setText("启动发现当前文件夹为空");
//				}
			  MessageUtil.updateMessage("启动发现当前文件夹为空");
		  }else if(logSize == 0 && allfile.length != 0) {
			  System.out.println("启动发现未处理图片---");
			  System.out.println("正在处理中..........请稍后");
//			  if(ClientGUI.area != null) {
//				  ClientGUI.area.appendText("启动发现未处理图片---");
//				  ClientGUI.area.appendText("正在处理中..........请稍后");
//			  }
			  MessageUtil.updateMessage("正在处理中..........请稍后");
			  for(File pic : allfile) {
				  if(!pic.isDirectory()) {
					  String fpath = PropertyUtil.TransferDirectoryPath + "/" + pic.getName();
					  if("OD".equals(pic.getName().substring(0, 2)) || 
								"OS".equals(pic.getName().substring(0, 2))) {
						  System.out.println("手持眼底图不进行处理");
					  }else {
						  transferPicture(pic.getName(), fpath, ctx);
					  }
				  }
			  }
		  }else if(logSize != 0 && allfile.length == 0) {
			  System.out.println("启动发现当前文件夹为空----不用处理"); 
//			  if(ClientGUI.area != null) {
//				  ClientGUI.area.appendText("启动发现当前文件夹为空----不用处理");
//			  }
//			  if(Constant.MESSAGE != null) {
//					Constant.M1.setText(Constant.M2.getText());
//					Constant.M2.setText("启动发现当前文件夹为空,不进行处理");
//			  }
			  MessageUtil.updateMessage("启动发现当前文件夹为空,不进行处理");
		  }else {
			  for(File pic : allfile) {					  
				  if(checkTransfer(pic, list)) {
					  if(!pic.isDirectory()) {
						  String fpath = PropertyUtil.TransferDirectoryPath + "/" + pic.getName();
						  if("OD".equals(pic.getName().substring(0, 2)) || 
									"OS".equals(pic.getName().substring(0, 2))) {
							  System.out.println("手持眼底图不进行处理");
//							  if(Constant.MESSAGE != null) {
//									Constant.M1.setText(Constant.M2.getText());
//									Constant.M2.setText("手持眼底图不进行处理");
//							  }
							  MessageUtil.updateMessage("手持眼底图不进行处理");
						  }else {
							  transferPicture(pic.getName(), fpath, ctx);
						  }
					  }
				  }
			  }
		  }
		  
		
		final Path path = Paths.get(PropertyUtil.TransferDirectoryPath);
		DecimalFormat df = new DecimalFormat("00000");
		
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE
					, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
			System.out.println("start----->");
//			 if(ClientGUI.area != null) {
//				  ClientGUI.area.appendText("\t\n" + "start----->");
//			  }
//			if(Constant.MESSAGE != null) {
//				Constant.M1.setText(Constant.M2.getText());
//				Constant.M2.setText("开始监听文件夹中-------");
//			}
			MessageUtil.updateMessage("开始监听文件夹中-------");
			while(true){
				final WatchKey key = watchService.take();
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
					final WatchEvent.Kind<?> kind = watchEvent.kind();
					if(kind == StandardWatchEventKinds.OVERFLOW) {
						System.out.println("OverFlow");
						continue;
					}else if(kind == StandardWatchEventKinds.ENTRY_CREATE) {
						System.out.println("create=====");
						
						WatchEvent<Path> watch = (WatchEvent<Path>) watchEvent;
						Path name = watch.context();
												
						
						Thread.sleep(1000);
						
                        String filePath = PropertyUtil.TransferDirectoryPath + "/" + name.toString();
                        
                        String[] str1 = filePath.split("[.]");
                        if(str1[str1.length - 1].toLowerCase().equals("jpg") || str1[str1.length - 1].toLowerCase().equals("png")) {
                        	//System.out.println("filePath=" + filePath);
    						BigInteger md5 = FileUtil.getMD5(filePath);
    						File srcFile = new File(filePath);
    						long size = srcFile.length();
    						//System.out.println("md5=" + md5 + ",size=" + size);
    						//System.out.println("name=" + name.toString());
    						String[] str = name.toString().split("[.]");
    						
    						byte[] attachment = FileUtil.fileToBytes(filePath);
    						System.out.println("attach=" + attachment.length);
    						String fileName = str[0] + "_" + md5 + "_" + size + "." + str[1];
    						//System.out.println(str.length + "=" + 999);
    						//String fileName = str[0] + "_" + size + "." + str[1];
    						//System.out.println(8888);
    						//System.out.println(fileName);
    						TransferMsg transferMsg = new TransferMsg();
    						transferMsg.setMsgType(MsgType.ImgTransfer);
    						transferMsg.setAttachment(FileUtil.gZip(attachment));
    						//transferMsg.setFileName(fileName);
    						PropertyUtil prop = new PropertyUtil();
    						if(prop.loadProperty()) {
    							if("OD".equals(name.toString().substring(0, 2)) || 
    									"OS".equals(name.toString().substring(0, 2))) {
    								System.out.println("UID记得输入");
    								Label label = Constant.UID;
    								String uid = label.getText();
    								if("".equals(uid)) {
    									System.out.println("UID有误");
    									Stage stage = Constant.STAGE;
    									Platform.runLater(new Runnable() {
    									    @Override
    									    public void run() {
    									    	DialogUtil.errorUID(stage, "未输入UID,请输入UID后进行操作");
    									    }
    									});
    								}else {
    									transferMsg.setFileName(PropertyUtil.SerialNumber + "," + name.toString() + 
        										"," + df.format((Integer.parseInt(uid))));
    								}
    							}else {
    								transferMsg.setFileName(PropertyUtil.SerialNumber + "," + name.toString());
    							}
//    							if(ClientGUI.area != null) {
//    								ClientGUI.area.appendText("\t\n" + "create=====");
//    								ClientGUI.area.appendText("\t\n" + PropertyUtil.SerialNumber + "," + name.toString() + "=" + size);
//    								ClientGUI.area.appendText("\t\n" + "attach=" + attachment.length);
//    							}
//    							if(Constant.MESSAGE != null) {
//									Constant.M1.setText(Constant.M2.getText());
//									Constant.M2.setText("create=====");
//    							}
//								if(Constant.MESSAGE != null) {
//									Constant.M1.setText(Constant.M2.getText());
//									Constant.M2.setText(PropertyUtil.SerialNumber + "," + name.toString()
//									+ "=" + size + "=" + "attach=" + attachment.length);
//								}
								MessageUtil.updateMessage("create...");
								MessageUtil.updateMessage(PropertyUtil.SerialNumber + "," + name.toString()
								+ "=" + size + "=" + "attach=" + attachment.length);
    							ctx.writeAndFlush(transferMsg);
    						}
                        }else {
                        	System.out.println("not a jpg file");
//                        	if(ClientGUI.area != null) {
//              				  ClientGUI.area.appendText("\t\n" + "not a jpg file");
//              			  }
//                        	if(Constant.MESSAGE != null) {
//                				Constant.M1.setText(Constant.M2.getText());
//                				Constant.M2.setText("当前文件不是图片");
//                			}
                        	MessageUtil.updateMessage("当前文件不是图片");
                        }
                        
						
						
//						String filePath = PropertyUtil.TransferDirectoryPath + "/" + name.toString();
//						
//						//BigInteger md5 = FileUtil.getMD5(filePath);
//						File srcFile = new File(filePath);
//						long size = srcFile.length();
//						//System.out.println("md5=" + md5 + ",size=" + size);
//						String[] str = name.toString().split(".");
//						
//						byte[] attachment = FileUtil.fileToBytes(filePath);
//						System.out.println("attach=" + attachment.length);
//						//String fileName = str[0] + "_" + md5 + "_" + size + "." + str[1];
//						String fileName = str[0] + "_" + size + "." + str[1];
//						TransferMsg transferMsg = new TransferMsg();
//						transferMsg.setAttachment(attachment);
//						transferMsg.setFileName(fileName);						
//						ctx.writeAndFlush(transferMsg);
						
					}else if(kind == StandardWatchEventKinds.ENTRY_DELETE) {
						System.out.println("deleted");
						lock.lock();
						try {							
							// 删除时重新更新table表
							TableDataUtil.resetData();
						}catch(Exception e) {
							e.printStackTrace();
						}
					}else if(kind == StandardWatchEventKinds.ENTRY_MODIFY) {
						System.out.println("modify=");
						WatchEvent<Path> watch = (WatchEvent<Path>) watchEvent;
						Path name = watch.context();
					}
					
					final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
					Path filename = watchEventPath.context();
					System.out.println(kind + "---->" + filename);
				}
				// reset the keyf
                boolean valid = key.reset();
                // exit loop if the key is not valid (if the directory was
                // deleted,for
                if (!valid) {
                    break;
                }
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private static void transferPicture(String filename, String fpath, ChannelHandlerContext ctx) {
		String[] str1 = filename.split("[.]");
        if(str1[str1.length - 1].toLowerCase().equals("jpg")) {
			File srcFile = new File(fpath);
			long size = srcFile.length();
			System.out.println(filename + "=" + size);
			
			byte[] attachment = FileUtil.fileToBytes(fpath);
			System.out.println("attach=" + attachment.length);
//			if(ClientGUI.area != null) {
//				  ClientGUI.area.appendText("\t\n" + filename + "=" + size);
//				  ClientGUI.area.appendText("\t\n" + "attach=" + attachment.length);
//			  }
			TransferMsg transferMsg = new TransferMsg();
			transferMsg.setMsgType(MsgType.ImgTransfer);
			transferMsg.setAttachment(FileUtil.gZip(attachment));
			PropertyUtil prop = new PropertyUtil();
			if(prop.loadProperty()) {
				transferMsg.setFileName(PropertyUtil.SerialNumber + "," + filename);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			if(Constant.MESSAGE != null) {
//				Constant.M1.setText(Constant.M2.getText());
//				Constant.M2.setText("图片传输中,请稍后...........");
//			}
			MessageUtil.updateMessage("图片传输中,请稍后...........");
			ctx.writeAndFlush(transferMsg);
        }else {
        	System.out.println("not a jpg file");
//        	if(ClientGUI.area != null) {
//				  ClientGUI.area.appendText("\t\n" + "not a jpg file");
//			  }
        }
		
	}

	public static boolean checkTransfer(File pic, List<String[]> list) {
		String name = pic.getName();
		boolean flag = true;
		for(String[] s : list) {
			if(name.equals(s[0])) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		//WatchImageService.process();
	}

}
