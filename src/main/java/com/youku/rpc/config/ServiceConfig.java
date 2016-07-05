package com.youku.rpc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.common.Const;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.server.Server;
import com.youku.rpc.remote.server.TypeObjectMapper;
import com.youku.rpc.remote.server.impl.NettyServer;

public class ServiceConfig<T> {

	private static final Logger log = LoggerFactory.getLogger(ServiceConfig.class);

	private Class<T> interfaceClass;

	private T ref;

	private RegistryConfig registryConfig;

	private ProtocolConfig protocolConfig;

	private ApplicationConfig applicationConfig;

	private int weight = 100;

	public ProtocolConfig getProtocolConfig() {
		return protocolConfig;
	}

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

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
			URL url = protocolConfig.toURL();
			String urlString = new StringBuilder().append(url.getIp()).append(':').append(url.getPort()).append('?')
					.append(Const.WEIGHT).append('=').append(weight).append('&').append(Const.SERIALIZER).append('=')
					.append(protocolConfig.getSerializer()).append('&').append(Const.PROTOCOL).append('=')
					.append(protocolConfig.getName()).toString();
			registryConfig.getRegistryService().register(new URL(urlString));
		}

		TypeObjectMapper.binding(interfaceClass.getName(), ref);
	}

	private void openServer() {
		URL url = protocolConfig.toURL();
		log.info("开启地址{}处的服务端口{}", url.getIp(), url.getPort());
		Server server = new NettyServer(url);
		server.open();
	}
}
