package com.youku.rpc.remote.protocol.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.server.HessianServlet;
import com.youku.rpc.common.Const;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.invoker.impl.HessianInvoker;
import com.youku.rpc.remote.protocol.Protocol;
import com.youku.rpc.remote.support.URL;

public class HessianProtocol implements Protocol {

	private static final Logger log = LoggerFactory.getLogger(HessianProtocol.class);

	@Override
	public Invoker refer(Class<?> interfaceClass, URL url) {
		log.info("使用hession协议引用服务");
		HessianProxyFactory factory = new HessianProxyFactory();
		int connectTimeout = url.getIntParam(Const.CONNECT_TIMEOUT_KEY, Const.DEFAULT_CONNECT_TIMEOUT);
		int timeout = url.getIntParam(Const.TIMEOUT_KEY, Const.DEFAULT_TIMEOUT);
		factory.setConnectTimeout(connectTimeout);
		factory.setReadTimeout(timeout);
		try {
			Object proxy = factory.create(interfaceClass, new StringBuilder().append("http://").append(url.getIp())
					.append(':').append(url.getPort()).toString());
			return new HessianInvoker(url, proxy, interfaceClass);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
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
