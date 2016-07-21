package com.youku.rpc.remote.protocol.impl;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.protocol.Protocol;
import com.youku.rpc.remote.support.URL;

public class ThriftProtocol implements Protocol {

	@Override
	public void export(Invoker invoker) {
		// URL url = invoker.getURL();
		// TServerTransport serverTransport = new TServerSocket(url.getPort());
		// TServer server = new TSimpleServer(new
		// Args(serverTransport).processor(processor));
		//
		// // Use this for a multithreaded server
		// // TServer server = new TThreadPoolServer(new
		// // TThreadPoolServer.Args(serverTransport).processor(processor));
		//
		// System.out.println("Starting the simple server...");
		// server.serve();

		// TODO not implement

	}

	@Override
	public Invoker refer(Class<?> interfaceClass, URL url) {
		// TODO not implement
		return null;
	}

}
