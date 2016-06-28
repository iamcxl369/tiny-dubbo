package com.youku.rpc.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.youku.rpc.client.Client;
import com.youku.rpc.client.impl.NettyClient;
import com.youku.rpc.cluster.Cluster;
import com.youku.rpc.cluster.Directory;
import com.youku.rpc.cluster.loadbalance.LoadBalance;
import com.youku.rpc.factory.ClusterFactory;
import com.youku.rpc.factory.LoadBalanceFactory;
import com.youku.rpc.factory.ProxyFactory;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.invoker.impl.DefaultInvoker;
import com.youku.rpc.net.URL;
import com.youku.rpc.registry.RegistryService;

public class ReferenceConfig<T> {

	private static final Logger log = LoggerFactory.getLogger(ReferenceConfig.class);

	private RegistryConfig registryConfig;

	private Cluster cluster = ClusterFactory.create("failover");;

	private int retryTimes = 3;

	private LoadBalance loadBalance = LoadBalanceFactory.create("random");

	private Class<?> interfaceClass;

	// 点对点直连的ip地址:端口
	private URL url;

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

		List<Client> clients = connectServers(servers);

		List<Invoker> invokers = wrapInvokers(servers, clients);

		Directory directory = new Directory(invokers, retryTimes, loadBalance);

		return cluster.join(directory);
	}

	private List<Invoker> wrapInvokers(List<URL> urls, List<Client> clients) {
		List<Invoker> invokers = new ArrayList<>(urls.size());
		for (int i = 0; i < urls.size(); i++) {
			Client client = clients.get(i);
			invokers.add(new DefaultInvoker(client, interfaceClass));
		}
		return invokers;
	}

	private List<Client> connectServers(List<URL> invokers) {
		List<Client> clients = new ArrayList<>(invokers.size());

		for (URL invoker : invokers) {
			Client client = new NettyClient(invoker);
			client.open();

			clients.add(client);
		}

		return clients;
	}

	private List<URL> lookUpServers() {
		List<URL> invokers = new ArrayList<>();
		if (url == null) {
			// 从zookeeper获取数据
			Assert.notNull(registryConfig, "注册中心不能为空");

			RegistryService registryService = registryConfig.getRegistryService();

			registryService.subscribe();

			invokers.addAll(registryService.getServers());
			log.info("从zookeeper取出服务提供者信息:{}", invokers);
		} else {
			invokers.add(url);
		}

		return invokers;
	}
}
