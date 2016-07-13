package com.youku.rpc.config;

import com.youku.rpc.common.Const;
import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.factory.ProxyFactory;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.protocol.Protocol;

public class ReferenceConfig<T> {

	private RegistryConfig registryConfig;

	private final Protocol registryProtocl = ExtensionLoader.getExtension(Protocol.class, Const.REGISTRY_PROTOCOL);

	private String cluster = "failover";

	private int retryTimes = 3;

	private String loadBalance = "random";

	private Class<T> interfaceClass;

	private String id;

	private ApplicationConfig applicationConfig;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getLoadBalance() {
		return loadBalance;
	}

	public void setLoadBalance(String loadBalance) {
		this.loadBalance = loadBalance;
	}

	public T get() {
		Invoker invoker = registryProtocl.refer(interfaceClass, getRegistryURL());
		return ProxyFactory.createProxy(invoker);
	}

	private URL getRegistryURL() {
		URL registryURL = new URL();
		registryURL.addParam(Const.CLUSTER, cluster);
		registryURL.addParam(Const.LOADBALANCE, loadBalance);
		registryURL.addParam(Const.RETRY_TIMES, String.valueOf(retryTimes));
		registryURL.setRegistryAddress(registryConfig.getAddress());
		registryURL.setRegistryProtocol(registryConfig.getProtocol());
		return registryURL;
	}

}
