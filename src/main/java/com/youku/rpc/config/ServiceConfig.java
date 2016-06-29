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

	private int weight = 100;

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

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
		register();
	}

	private void register() {
		if (registryConfig != null) {
			log.info("注册服务");
			URL url = protocolConfig.getUrl();
			String urlString = new StringBuilder().append(url.getIp()).append(':').append(url.getPort()).append('?')
					.append("weight=").append(weight).toString();
			registryConfig.getRegistryService().register(new URL(urlString));
		}

		TypeObjectMapper.binding(interfaceClass, ref);
	}

	private void openServer() {
		URL url = protocolConfig.getUrl();
		log.info("开启地址{}处的服务端口{}", url.getIp(), url.getPort());
		Server server = new NettyServer(url);
		server.open();
	}
}
