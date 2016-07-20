package com.youku.rpc.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

import com.youku.rpc.common.Const;
import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.factory.ProxyFactory;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.protocol.Protocol;
import com.youku.rpc.remote.support.URL;

public class ReferenceConfig<T> {

	private RegistryConfig registryConfig;

	private final Protocol registryProtocol = ExtensionLoader.getExtension(Protocol.class, Const.REGISTRY_PROTOCOL_KEY);

	private Map<String, String> attachments = new HashMap<>();

	private ApplicationConfig applicationConfig;

	public void addAttachment(String key, String value) {
		attachments.put(key, value);
	}

	public String getAttachment(String key) {
		return attachments.get(key);
	}

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public RegistryConfig getRegistryConfig() {
		return registryConfig;
	}

	public void setRegistryConfig(RegistryConfig registryConfig) {
		this.registryConfig = registryConfig;
	}

	public T get() {
		String interfaceName = attachments.get(Const.INTERFACE_KEY);
		Assert.notNull(interfaceName, "interface不能为空");
		Class<?> interfaceClass = null;
		try {
			interfaceClass = Class.forName(interfaceName);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("不存在此interface", e);
		}
		Invoker invoker = registryProtocol.refer(interfaceClass, getRegistryURL());
		return ProxyFactory.createProxy(invoker);
	}

	private URL getRegistryURL() {
		URL registryURL = new URL();
		for (Entry<String, String> entry : attachments.entrySet()) {
			registryURL.addParam(entry.getKey(), entry.getValue());
		}

		registryURL.setRegistryAddress(registryConfig.getAddress());
		registryURL.setRegistryProtocol(registryConfig.getProtocol());
		return registryURL;
	}

}
