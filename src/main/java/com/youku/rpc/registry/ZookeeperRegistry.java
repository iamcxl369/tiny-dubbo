package com.youku.rpc.registry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.common.Const;
import com.youku.rpc.net.URL;

public class ZookeeperRegistry implements RegistryService {

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
		log.info("注册数据{}", url);
		createNode(url);
	}

	private void createNode(URL url) {
		String data = url.getIp() + ":" + url.getPort();
		create(Const.ZK_DATA_PATH, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	}

	private void create(String path, byte[] bytes, ArrayList<ACL> acl, CreateMode mode) {
		int index = path.lastIndexOf('/');

		if (index > 0) {
			create(path.substring(0, index), null, acl, mode);
		}

		try {
			zk.create(path, bytes, acl, mode);
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
		log.info("订阅消息");
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

		datas = new ArrayList<>(nodes.size());
		for (String node : nodes) {
			try {
				datas.add(new String(zk.getData(Const.ZK_REGISTRY_PATH + "/" + node, false, null)));
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		log.info("完成通知，数据为{}", datas);
	}

	public List<URL> getServers() {
		List<URL> servers = new ArrayList<>(datas.size());

		for (String url : datas) {
			String[] arr = StringUtils.split(url, ':');
			String ip = arr[0];
			int port = Integer.parseInt(arr[1]);

			servers.add(new URL(ip, port));
		}

		return servers;
	}
}
