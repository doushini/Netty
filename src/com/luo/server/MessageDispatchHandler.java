package com.luo.server;

import org.jboss.netty.channel.MessageEvent;

public class MessageDispatchHandler {

	private ChatData chatData;
	private ChatConnection connection;
	private ChartRoomListener chartRoomListener;
	
	public void setChartRoomListener(ChartRoomListener chartRoomListener) {
		this.chartRoomListener = chartRoomListener;
	}

	public MessageDispatchHandler(MessageEvent e) {
		chatData = ChatPackageUtil.unpackage(e.getMessage());
	}

	public void dispatch() {
		
	}
}
