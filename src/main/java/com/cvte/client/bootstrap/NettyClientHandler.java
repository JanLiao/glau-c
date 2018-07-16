package com.cvte.client.bootstrap;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cvte.client.gui.ClientGUI;
import com.cvte.client.util.Constant;
import com.cvte.client.util.LoginUtil;
import com.cvte.client.util.OpenPDF;
import com.cvte.client.util.PropertyUtil;
import com.cvte.client.util.ReadCSV;
import com.cvte.client.util.SaveToCSV;
import com.cvte.client.util.TableDataUtil;
import com.cvte.client.util.TransferFileHandler;
import com.cvte.netty.msg.HeartBeatMsg;
import com.cvte.netty.msg.ImgInfo;
import com.cvte.netty.msg.LoggerInfo;
import com.cvte.netty.msg.PDFRecMsg;
import com.cvte.netty.msg.ResultMsg;
import com.cvte.netty.msg.TransferMsg;
import com.cvte.netty.msg.ValidateMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	
	private final static Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);
	
	public static ChannelHandlerContext channelcx = null;
	
	@Override
    public void channelActive(ChannelHandlerContext ctx) {   //一建立连接就发送该终端的信息, 以验证该终端可以连接服务器
		channelcx = ctx;
		com.cvte.client.constant.Constant.ctx = ctx;
		ctx.writeAndFlush(new ValidateMsg(PropertyUtil.SerialNumber, PropertyUtil.ConnectPassword));  
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
    	ResultMsg resultMsg = (ResultMsg) msg;
    	System.out.println("replyType=" + resultMsg.getReplyType());
    	if(ClientGUI.area != null) {
			  ClientGUI.area.appendText("\t\n" + "replyType=" + resultMsg.getReplyType());
		  }
        switch(resultMsg.getReplyType()) {
          case Validate:
        	  if(resultMsg.isSuccess()) {
        		  Constant.setTerminalId(PropertyUtil.SerialNumber);  //验证成功， 保存终端的主键

				  File f = new File(PropertyUtil.Save_Pdf_Path);
				  File f2 = new File(PropertyUtil.TransferDirectoryPath);
				  //File f3 = new File(PropertyUtil.Img_Result_Path);
				  File f4 = new File(PropertyUtil.Logger_Path);
				  
				  if(!f2.exists()) {
					  f2.mkdirs();
				  }
				  if (!f.exists()) {
					  f.mkdirs();
				  }
//				  if (!f3.exists()) {
//					  f3.mkdirs();
//				  }
				  if (!f4.exists()) {
					  f4.mkdirs();
				  }
        		  
        		  new Thread() {
						@Override
						public void run() {							
							//开始watch文件夹是否有文件
							WatchImageService.process(ctx);
						}
        		   }.start();
        		 
        		  //System.out.println(resultMsg.getData().get(DataKey.vediofiles).toString());
        		  System.out.println("validate success! " + Constant.getTerminalId());
        		  if(ClientGUI.area != null) {
      				  ClientGUI.area.appendText("\t\n" + "validate success! " + Constant.getTerminalId());
      			  }
        	  } else {
        		  //JOptionPane.showMessageDialog(null, resultMsg.getMsg(), "验证失败", JOptionPane.ERROR_MESSAGE); 
        		  LoginUtil.loginText.setText("账号不存在或密码错误");
        		  //断开netty的连接
        		  ctx.close();
        	  }
        	  break;
        	
          case ImgTransfer:
				TransferMsg transferMsg = (TransferMsg)resultMsg.getData().get("imgdata");
				System.out.println("收到pdf=" + transferMsg.getFileName() + "=" + transferMsg.getAttachment());
				if(ClientGUI.area != null) {
    				  ClientGUI.area.appendText("\t\n" + "收到pdf=" + transferMsg.getFileName() + "=" + transferMsg.getAttachment());
    			  }
				TransferFileHandler.saveImage(transferMsg);
				
				Calendar cal = Calendar.getInstance();
				 int year = cal.get(Calendar.YEAR);
				 int month = cal.get(Calendar.MONTH )+1;
				 int date = cal.get(Calendar.DATE);
				 String pdfname = transferMsg.getFileName().split(",")[0];
				 String[] nname = pdfname.split("[.]");
				 char[] pdf = nname[0].toCharArray();
				 String namen = "" + pdf[pdf.length - 1] + pdf[pdf.length - 2];
				 if("LR".equals(namen) || "RL".equals(namen)) {
					 String pdfpath = PropertyUtil.Save_Pdf_Path + "/" + year +"-" + month + "-" + date + "/" +transferMsg.getFileName().split(",")[0]; 
					 OpenPDF.readPDF(pdfpath);
					 
					 //LR两只眼睛聚齐删除单眼PDF
//					 File[] files = new File(PropertyUtil.Save_Pdf_Path + "/" + year +"-" + month + "-" + date).listFiles();
//					 for(File file : files) {
//						 if(!file.getName().equals(pdfname)) {
//							 if(file.getName().split("_")[0].equals(pdfname.split("_")[0])) {
//								 file.delete();
//							 }
//						 }
//					 }
					 
				 }
				//PDF文件接收成功
				ctx.writeAndFlush(new PDFRecMsg(transferMsg.getFileName()));
				break;
				
          case ImgMessage:
        	  if(resultMsg.isSuccess()) {
//        		  ImgInfo info = (ImgInfo) resultMsg.getData().get("imgMessage");
//        		  System.out.println("csv信息=" + info);
//        		  SaveToCSV.save(info);
        		  System.out.println("data=" + resultMsg.getMsg() + "==" + resultMsg.getData());
        		  LoggerInfo log = (LoggerInfo) resultMsg.getData().get("imgMessage");
        		  System.out.println("收到处理信息log----" + log);
        		  if(ClientGUI.area != null) {
					  ClientGUI.area.appendText("\t\n" + "收到处理信息log----" + log);
				  }
        		  SaveToCSV.saveLog(log);
        		  
        		  // 保存完logger日志后  刷新表格数据
        		  TableDataUtil.resetData();
        	  }
        	  
          default:
        	  //System.out.println("客户端接受到未知消息");
        	  break;
        }
    	
    }

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
//	public static void exceptionCaught(ChannelHandlerContext ctx) {
//        ctx.close();
//    }
    
    
    private static final HeartBeatMsg heartBeatMsg = new HeartBeatMsg();   //心跳包         
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {  //定时发送心跳包
        if (evt instanceof IdleStateEvent) {
        	
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(heartBeatMsg);  //发送心跳信息
            }
            
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
    
}
