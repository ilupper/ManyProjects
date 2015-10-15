package com.ilupper.restful;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class StartingJetty extends AbstractHandler {

	public void handle(String target, Request baseRequest, HttpServletRequest request, 
			HttpServletResponse response) throws IOException, ServletException
			{
				response.setContentType("text/html; charset=utf-8");
				response.setStatus(HttpServletResponse.SC_OK);
				baseRequest.setHandled(true);
				//response.getWriter().println("<h1>The Response Request</h1>");
				response.getWriter().println(new SimpleService().getTrainOnboard());
			}

	public static void main(String[] args) {
		Server server = new Server(new InetSocketAddress("localhost", 8080));
		server.setHandler(new StartingJetty());
		
		try {
			server.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
