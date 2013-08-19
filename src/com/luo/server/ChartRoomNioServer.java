package com.luo.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.util.CharsetUtil;

public class ChartRoomNioServer {

	private static final int SERVER_PORT=9090;
	
	/**
	 * Netty的一些通用配置
	 * Author:罗辉 ,Date:2013-7-25
	 */
	public void start(){
		System.out.println("启动服务器。。。");
		ServerBootstrap bootstrap=new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
		        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.zeroDelimiter()));//netty没有提供\0截取。不过重写部分代码即可
		        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
		        pipeline.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
		        pipeline.addLast("messageHandler", new MessageHandler());
//		        pipeline.addLast("encoder", new MessageEncoder());
		        return pipeline;
			}
		});
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setOption("reuseAddress", true);
		bootstrap.bind(new InetSocketAddress(SERVER_PORT));
	}
}
