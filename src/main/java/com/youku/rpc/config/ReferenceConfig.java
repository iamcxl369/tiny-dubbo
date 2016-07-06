package com.youku.rpc.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.youku.rpc.common.Const;
import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.factory.ClusterFactory;
import com.youku.rpc.factory.LoadBalanceFactory;
import com.youku.rpc.factory.ProxyFactory;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.registry.Registry;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.cluster.Cluster;
import com.youku.rpc.remote.cluster.Directory;
import com.youku.rpc.remote.cluster.loadbalance.LoadBalance;
import com.youku.rpc.remote.protocol.Protocol;

public class ReferenceConfig<T> {

	private static final Logger log = LoggerFactory.getLogger(ReferenceConfig.class);

	private RegistryConfig registryConfig;

	private Cluster cluster = ClusterFactory.create("failover");

	private int retryTimes = 3;

	private LoadBalance loadBalance = LoadBalanceFactory.create("random");

	private Class<T> interfaceClass;

	private String id;

	// 点对点直连的ip地址:端口
	private URL url;

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

	public URL getUrl() {
		return url;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public void createCluster(String clusterName) {
		cluster = ClusterFactory.create(clusterName);
	}

	public void createLoadBalance(String loadBalanceName) {
		loadBalance = LoadBalanceFactory.create(loadBalanceName);
	}

	public T get() {
		Invoker invoker = connectAndWrap();

		return ProxyFactory.createProxy(invoker);
	}

	private Invoker connectAndWrap() {
		List<URL> servers = lookUpServers();

		Assert.isTrue(!servers.isEmpty(), "没有服务提供者");

		List<Invoker> invokers = new ArrayList<>(servers.size());

		for (URL server : servers) {
			Protocol protocol = ExtensionLoader.getExtension(Protocol.class, server.getParam(Const.PROTOCOL));

			invokers.add(protocol.refer(interfaceClass, server));
		}

		Directory directory = new Directory(invokers, retryTimes, loadBalance);

		return cluster.join(directory);
	}

	private List<URL> lookUpServers() {
		List<URL> invokers = new ArrayList<>();
		if (url == null) {
			// 从zookeeper获取数据
			Assert.notNull(registryConfig, "注册中心不能为空");

			Registry registryService = registryConfig.getRegistryService();

			registryService.subscribe();

			invokers.addAll(registryService.getServers());
			log.info("从zookeeper取出服务提供者信息:{}", invokers);
		} else {
			invokers.add(url);
		}

		return invokers;
	}
}
