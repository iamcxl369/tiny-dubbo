package com.youku.rpc.remote.protocol.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.youku.rpc.common.Const;
import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.factory.RegistryFactory;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.registry.Registry;
import com.youku.rpc.remote.cluster.Cluster;
import com.youku.rpc.remote.cluster.Directory;
import com.youku.rpc.remote.cluster.loadbalance.LoadBalance;
import com.youku.rpc.remote.protocol.Protocol;
import com.youku.rpc.remote.support.URL;

public class RegistryProtocol implements Protocol {

	private static final Logger log = LoggerFactory.getLogger(RegistryProtocol.class);

	@Override
	public void export(Invoker invoker) {
		URL url = invoker.getURL();
		log.info("注册数据{}", url);
		Registry registry = ExtensionLoader.getExtension(RegistryFactory.class, url.getRegistryProtocol())
				.getRegistry(url);

		registry.register(url);

	}

	@Override
	public Invoker refer(Class<?> interfaceClass, URL url) {
		log.info("订阅数据");

		List<URL> servers = lookUpServers(url);

		Assert.isTrue(!servers.isEmpty(), "没有服务提供者");

		List<Invoker> invokers = new ArrayList<>(servers.size());

		for (URL server : servers) {
			Protocol protocol = ExtensionLoader.getExtension(Protocol.class, server.getParam(Const.PROTOCOL_KEY));

			URL combinedURL = combine(url, server);

			invokers.add(protocol.refer(interfaceClass, combinedURL));
		}

		int retryTimes = url.getIntParam(Const.RETRY_TIMES_KEY, Const.DEFAULT_RETRY_TIMES);
		LoadBalance loadBalance = ExtensionLoader.getExtension(LoadBalance.class,
				url.getParam(Const.LOADBALANCE_KEY, Const.DEFAULT_LOAD_BALANCE));
		Cluster cluster = ExtensionLoader.getExtension(Cluster.class,
				url.getParam(Const.CLUSTER_KEY, Const.DEFAULT_CLUSTER));

		Directory directory = new Directory(invokers, retryTimes, loadBalance);

		return cluster.join(directory);
	}

	private URL combine(URL client, URL server) {
		for (Entry<String, String> entry : client.getParams().entrySet()) {
			server.addParam(entry.getKey(), entry.getValue());
		}
		return server;
	}

	private List<URL> lookUpServers(URL url) {
		Registry registry = ExtensionLoader.getExtension(RegistryFactory.class, url.getRegistryProtocol())
				.getRegistry(url);

		registry.subscribe();

		List<URL> invokers = new ArrayList<>();

		invokers.addAll(registry.getServers());
		log.info("从{}取出服务提供者信息:{}", url.getRegistryProtocol(), invokers);
		return invokers;
	}

}
