package com.cvte.reference.idle;

import java.util.concurrent.TimeUnit;

import com.cvte.client.constant.Constant;
import com.cvte.client.gui.ClientGUI;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

/**
 * 
 * 重连检测狗，当发现当前的链路不稳定关闭之后，进行12次重连
 */
@Sharable
public abstract class ConnectionWatchdog extends ChannelInboundHandlerAdapter implements TimerTask ,ChannelHandlerHolder{
    
    
    
    private final Bootstrap bootstrap;
    private final Timer timer;
    private final int port;
    
    private final String host;

    private volatile boolean reconnect = true;
    private int attempts;
    
    
    public ConnectionWatchdog(Bootstrap bootstrap, Timer timer, int port,String host, boolean reconnect) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.port = port;
        this.host = host;
        this.reconnect = reconnect;
    }
    
    /**
     * channel链路每次active的时候，将其连接的次数重新☞ 0
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        
        System.out.println("当前链路已经激活了，重连尝试次数重新置为0");
        if(ClientGUI.area != null) {
			  ClientGUI.area.appendText("当前链路已经激活了，重连尝试次数重新置为0");
		  }
        attempts = 0;
        ctx.fireChannelActive();
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接关闭");

        if(Constant.flag == 1) {
        	System.out.println("客户端主动关闭连接");
        }else if (Constant.flag == 0){
        	if(reconnect){
                System.out.println("链接关闭，将进行重连");
                if(ClientGUI.area != null) {
      			  ClientGUI.area.appendText("\t\n" + "链接关闭，将进行重连");
      		  }

                if (attempts < 12) {
                    attempts++;
                    //重连的间隔时间会越来越长
                    int timeout = 2 << attempts;
                    timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
                }
            }
            ctx.fireChannelInactive();
        }
    }
    

    public void run(Timeout timeout) throws Exception {
        
        ChannelFuture future;
        //bootstrap已经初始化好了，只需要将handler填入就可以了
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    
                    ch.pipeline().addLast(handlers());
                }
            });
            future = bootstrap.connect(host,port);
        }
        //future对象
        future.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture f) throws Exception {
                boolean succeed = f.isSuccess();

                //如果重连失败，则调用ChannelInactive方法，再次出发重连事件，一直尝试12次，如果失败则不再重连
                if (!succeed) {
                    System.out.println("重连失败");
                    if(ClientGUI.area != null) {
            			  ClientGUI.area.appendText("\t\n" + "重连失败");
            		  }
                    f.channel().pipeline().fireChannelInactive();
                }else{
                    System.out.println("重连成功");
                    if(ClientGUI.area != null) {
            			  ClientGUI.area.appendText("\t\n" + "重连成功");
            		  }
                }
            }
        });
        
    }

}
