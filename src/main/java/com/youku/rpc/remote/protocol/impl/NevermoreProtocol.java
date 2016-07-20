package com.youku.rpc.remote.protocol.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.invoker.impl.NevermoreInvoker;
import com.youku.rpc.remote.client.Client;
import com.youku.rpc.remote.client.impl.NettyClient;
import com.youku.rpc.remote.protocol.Protocol;
import com.youku.rpc.remote.server.Server;
import com.youku.rpc.remote.server.impl.NettyServer;
import com.youku.rpc.remote.support.URL;

public class NevermoreProtocol implements Protocol {

	private static final Logger log = LoggerFactory.getLogger(NevermoreProtocol.class);

	@Override
	public Invoker refer(Class<?> interfaceClass, URL server) {
		log.info("使用nevermore协议引用服务");
		Client client = new NettyClient(server);
		client.open();
		return createInvoker(client, server, interfaceClass);
	}

	private Invoker createInvoker(Client client, URL server, Class<?> interfaceClass) {
		return new NevermoreInvoker(server, client, null, interfaceClass);
	}

	@Override
	public void export(Invoker invoker) {
		log.info("使用nevermore协议暴露服务");
		URL url = invoker.getURL();
		log.info("开启地址{}处的服务端口{}", url.getIp(), url.getPort());
		Server server = new NettyServer(url);
		server.open();
	}

}
