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
import java.util.List;

import org.joda.time.DateMidnight.Property;

import com.cvte.client.gui.ClientGUI;
import com.cvte.client.util.FileUtil;
import com.cvte.client.util.PropertyUtil;
import com.cvte.client.util.ReadCSV;
import com.cvte.netty.msg.MsgType;
import com.cvte.netty.msg.TransferMsg;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author jan
 * @date 2018年3月19日 上午1:48:40
 * @version V1.0 
 */
public class WatchImageService {
	
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
			  if(ClientGUI.area != null) {
				  ClientGUI.area.appendText("启动发现当前文件夹为空----");
			  }
		  }else if(logSize == 0 && allfile.length != 0) {
			  System.out.println("启动发现未处理图片---");
			  System.out.println("正在处理中..........请稍后");
			  if(ClientGUI.area != null) {
				  ClientGUI.area.appendText("启动发现未处理图片---");
				  ClientGUI.area.appendText("正在处理中..........请稍后");
			  }
			  for(File pic : allfile) {
				  if(!pic.isDirectory()) {
					  String fpath = PropertyUtil.TransferDirectoryPath + "/" + pic.getName();
					  transferPicture(pic.getName(), fpath, ctx);
				  }
			  }
		  }else if(logSize != 0 && allfile.length == 0) {
			  System.out.println("启动发现当前文件夹为空----不用处理"); 
			  if(ClientGUI.area != null) {
				  ClientGUI.area.appendText("启动发现当前文件夹为空----不用处理");
			  }
		  }else {
			  for(File pic : allfile) {					  
				  if(checkTransfer(pic, list)) {
					  if(!pic.isDirectory()) {
						  String fpath = PropertyUtil.TransferDirectoryPath + "/" + pic.getName();
						  transferPicture(pic.getName(), fpath, ctx);
					  }
				  }
			  }
		  }
		  
		
		final Path path = Paths.get(PropertyUtil.TransferDirectoryPath);
		
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE
					, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.OVERFLOW);
			System.out.println("start----->");
			 if(ClientGUI.area != null) {
				  ClientGUI.area.appendText("\t\n" + "start----->");
			  }
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
                        if(str1[str1.length - 1].toLowerCase().equals("jpg")) {
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
    						transferMsg.setFileName(PropertyUtil.SerialNumber + "," + name.toString());
    						if(ClientGUI.area != null) {
    							ClientGUI.area.appendText("\t\n" + "create=====");
    							  ClientGUI.area.appendText("\t\n" + PropertyUtil.SerialNumber + "," + name.toString() + "=" + size);
    							  ClientGUI.area.appendText("\t\n" + "attach=" + attachment.length);
    						  }
    						ctx.writeAndFlush(transferMsg);
                        }else {
                        	System.out.println("not a jpg file");
                        	if(ClientGUI.area != null) {
              				  ClientGUI.area.appendText("\t\n" + "not a jpg file");
              			  }
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
			if(ClientGUI.area != null) {
				  ClientGUI.area.appendText("\t\n" + filename + "=" + size);
				  ClientGUI.area.appendText("\t\n" + "attach=" + attachment.length);
			  }
			TransferMsg transferMsg = new TransferMsg();
			transferMsg.setMsgType(MsgType.ImgTransfer);
			transferMsg.setAttachment(FileUtil.gZip(attachment));
			transferMsg.setFileName(filename);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ctx.writeAndFlush(transferMsg);
        }else {
        	System.out.println("not a jpg file");
        	if(ClientGUI.area != null) {
				  ClientGUI.area.appendText("\t\n" + "not a jpg file");
			  }
        }
		
	}

	private static boolean checkTransfer(File pic, List<String[]> list) {
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
