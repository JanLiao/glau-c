package com.cvte.client.bootstrap;

import java.util.concurrent.TimeUnit;

import com.cvte.client.util.PropertyUtil;
import com.cvte.reference.idle.ConnectionWatchdog;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;


public class NettyClient {  //netty的客户端
	
    protected final HashedWheelTimer timer = new HashedWheelTimer();  
    
    private Bootstrap boot;  
  
    public void connect(int port, String host) throws Exception {  
          
        EventLoopGroup group = new NioEventLoopGroup();    
          
        boot = new Bootstrap();  
        boot.group(group).channel(NioSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO));  
              
        final ConnectionWatchdog watchdog = new ConnectionWatchdog(boot, timer, port, host, true) {  
  
                public ChannelHandler[] handlers() {  
                	
                    return new ChannelHandler[] {  
                            this,  
                            new IdleStateHandler(0, 60, 0, TimeUnit.SECONDS),  
                            new ObjectEncoder(),
                            new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)),
                            new NettyClientHandler()
                    };  
                }  
            };  
              
            ChannelFuture future;  
            //进行连接  
            try {  
                synchronized (boot) {  
                    boot.handler(new ChannelInitializer<Channel>() {  
  
                        //初始化channel  
                        @Override  
                        protected void initChannel(Channel ch) throws Exception {  
                            ch.pipeline().addLast(watchdog.handlers());  
                        }  
                    });  
  
                    future = boot.connect(host,port);  
                }  
  
                // 以下代码在synchronized同步块外面是安全的  
                future.sync();
             
            } catch (Throwable t) {  
                throw new Exception("connects to  fails", t);  
            }
            /* 
            finally {
                group.shutdownGracefully();
            }
            */
            
    }
    
    
    
    public static void main(String args[]) throws Exception {
    	PropertyUtil propertyUtil = new PropertyUtil();
        if(propertyUtil.loadProperty()) {
    	   NettyClient nettyClient = new NettyClient();
           nettyClient.connect(PropertyUtil.ServerNettyPort, PropertyUtil.ServerIP);
        }
    }
    
    
    
    
}
