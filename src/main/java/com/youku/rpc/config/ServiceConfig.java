package com.youku.rpc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.net.URL;
import com.youku.rpc.server.Server;
import com.youku.rpc.server.TypeObjectMapper;
import com.youku.rpc.server.impl.NettyServer;

public class ServiceConfig<T> {

	private static final Logger log = LoggerFactory.getLogger(ServiceConfig.class);

	private Class<T> interfaceClass;

	private T ref;

	private RegistryConfig registryConfig;

	private ProtocolConfig protocolConfig;

	public Class<T> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public T getRef() {
		return ref;
	}

	public void setRef(T ref) {
		this.ref = ref;
	}

	public RegistryConfig getRegistryConfig() {
		return registryConfig;
	}

	public void setRegistryConfig(RegistryConfig registryConfig) {
		this.registryConfig = registryConfig;
	}

	public void setProtocolConfig(ProtocolConfig protocolConfig) {
		this.protocolConfig = protocolConfig;
	}

	/**
	 * 暴露服务
	 */
	public void export() {
		// 开启netty服务器
		openServer();

		// 去注册中心注册服务
		registerService();
	}

	private void registerService() {
		log.info("注册服务");
		TypeObjectMapper.binding(interfaceClass, ref);
	}

	private void openServer() {
		String ip = protocolConfig.getIp();
		int port = protocolConfig.getPort();
		log.info("开启地址{}处的服务端口{}", ip, port);
		Server server = new NettyServer(new URL(ip, port));
		server.open();
	}
}
