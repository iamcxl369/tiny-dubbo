package com.youku.rpc.registry.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.common.Const;
import com.youku.rpc.registry.Registry;
import com.youku.rpc.remote.URL;

public class ZookeeperRegistry implements Registry {

	private ZooKeeper zk;

	private String registryAddress;

	private CountDownLatch connectedSignal = new CountDownLatch(1);

	private List<String> datas = new ArrayList<>();

	private static final Logger log = LoggerFactory.getLogger(ZookeeperRegistry.class);

	public ZookeeperRegistry(String registryAddress) {
		this.registryAddress = registryAddress;
		connectServer();
	}

	@Override
	public void register(URL url) {
		createNode(url);
	}

	private void createNode(URL url) {
		String data = url.toURLString();
		create(Const.ZK_REGISTRY_PATH + "/" + data, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
	}

	private void create(String path, byte[] bytes, ArrayList<ACL> acl, CreateMode mode) {
		int index = path.lastIndexOf('/');

		if (index > 0) {
			create(path.substring(0, index), null, acl, CreateMode.PERSISTENT);
		}

		try {
			log.info("创建path={}", path);
			zk.create(path, bytes, acl, mode);
		} catch (NodeExistsException e) {

		} catch (KeeperException | InterruptedException e) {
			log.error("创建节点报错", e);
		}
	}

	private void connectServer() {
		try {
			zk = new ZooKeeper(registryAddress, Const.ZK_SESSION_TIMEOUT, new Watcher() {

				@Override
				public void process(WatchedEvent event) {
					if (event.getState() == KeeperState.SyncConnected) {
						connectedSignal.countDown();
					}

				}
			});

			connectedSignal.await();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void subscribe() {
		List<String> nodes = null;
		try {
			nodes = zk.getChildren(Const.ZK_REGISTRY_PATH, new Watcher() {

				@Override
				public void process(WatchedEvent event) {
					if (event.getType() == Event.EventType.NodeChildrenChanged) {
						log.info("开始通知");
						subscribe();
					}
				}

			});

		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException(e);
		}

		datas = nodes;
	}

	@Override
	public List<URL> getServers() {
		List<URL> servers = new ArrayList<>(datas.size());

		for (String urlString : datas) {
			servers.add(new URL(urlString));
		}

		return servers;
	}
}
