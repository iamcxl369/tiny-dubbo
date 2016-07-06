package com.youku.rpc.remote.protocol.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.server.HessianServlet;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.invoker.impl.HessianInvoker;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.protocol.Protocol;

public class HessianProtocol implements Protocol {

	private static final Logger log = LoggerFactory.getLogger(HessianProtocol.class);

	@Override
	public Invoker refer(Class<?> interfaceClass, URL url) {
		log.info("使用hession协议引用服务");
		return new HessianInvoker(url, null, interfaceClass);
	}

	@Override
	public void export(Invoker invoker) {
		log.info("使用hession协议暴露服务");
		Server server = new Server(invoker.getURL().getPort());

		ServletHandler handler = new ServletHandler();

		server.setHandler(handler);

		ServletHolder holder = new ServletHolder(HessianServlet.class);
		Map<String, String> initParams = new HashMap<>(2);
		initParams.put("home-class", invoker.getTargetEntity().getClass().getName());
		initParams.put("home-api", invoker.getInterfaceClass().getName());
		holder.setInitParameters(initParams);

		handler.addServletWithMapping(holder, "/*");

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
