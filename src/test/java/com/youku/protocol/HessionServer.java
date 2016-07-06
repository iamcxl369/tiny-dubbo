package com.youku.protocol;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.caucho.hessian.server.HessianServlet;

public class HessionServer {

	public static void main(String[] args) throws Exception {

		Server server = new Server(8080);

		ServletHandler handler = new ServletHandler();

		server.setHandler(handler);

		ServletHolder holder = new ServletHolder(HessianServlet.class);
		Map<String, String> initParams = new HashMap<>(2);
		initParams.put("home-class", "com.youku.protocol.BasicService");
		initParams.put("home-api", "com.youku.protocol.BasicAPI");
		holder.setInitParameters(initParams);

		handler.addServletWithMapping(holder, "/*");

		server.start();

		server.join();
	}

}
