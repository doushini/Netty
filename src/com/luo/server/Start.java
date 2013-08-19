package com.luo.server;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}
	public void init() throws ServletException {
		ChartRoomNioServer server=new ChartRoomNioServer();
		server.start();
	}
}
