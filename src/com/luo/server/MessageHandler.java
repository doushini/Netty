package com.luo.server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * 逻辑处理：
	1. 处理flash 的policy file request
	2. 处理业务逻辑
 * @author 罗辉, @date:2013-7-25
 *
 */
public class MessageHandler extends SimpleChannelUpstreamHandler {

	private static final String    POLICY_REQUEST    = "<policy-file-request/>";
	private static final String    POLICY_XML        = "<?xml version=\"1.0\"?><cross-domain-policy><site-con";
	
	 @Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		 ChatService.initConnection(e.getChannel());
	}
	 @Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		 ChatService.closedConnection(e.getChannel());
	}
	 
	 @Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		 String msg=(String) e.getMessage();
		 if(msg.equalsIgnoreCase(POLICY_REQUEST)){
			 e.getChannel().write(POLICY_XML+"\0");//flex以\0结尾
			 e.getChannel().close();
		 }else{
			 MessageDispatchHandler dispatchHandler=new MessageDispatchHandler(e);
			 dispatchHandler.setChartRoomListener(new chartRoomListenerImpl());
			 dispatchHandler.dispatch();
		 }
	}
	 
	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, ExceptionEvent e)
			throws Exception {
		e.getChannel().close();
	}
}
