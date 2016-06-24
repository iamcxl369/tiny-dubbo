package com.youku.rpc.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.youku.rpc.client.Client;
import com.youku.rpc.client.Invoker;
import com.youku.rpc.client.ProxyFactory;
import com.youku.rpc.client.impl.NettyClient;
import com.youku.rpc.net.URL;

public class ReferenceConfig<T> {

	private RegistryConfig registryConfig;

	private Class<?> interfaceClass;

	// 点对点直连的ip地址:端口
	private String url;

	public RegistryConfig getRegistryConfig() {
		return registryConfig;
	}

	public void setRegistryConfig(RegistryConfig registryConfig) {
		this.registryConfig = registryConfig;
	}

	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<?> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public T get() {
		// 打通netty客户端到服务端的连接
		Client client = connectServer();
		
		return ProxyFactory.createProxy(new Invoker(client,interfaceClass));
	}

	private Client connectServer() {
		String ip = null;
		int port = 0;
		if (StringUtils.isBlank(url)) {
			// 从zookeeper获取数据
		} else {
			String[] arr = StringUtils.split(url, ':');
			Assert.isTrue(arr.length == 2, "直连的url格式为ip:port");

			ip = arr[0];
			try {
				port = Integer.parseInt(arr[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("直连的url格式为ip:port，port端口必须是数字类型", e);
			}
		}
		Client client = new NettyClient(new URL(ip, port));

		client.open();

		return client;
	}
}
