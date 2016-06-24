package com.youku.rpc.registry;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.common.Const;
import com.youku.rpc.net.URL;

public class ZookeeperRegistry implements RegistryService {

	private ZooKeeper zk;

	private String registryAddress;

	private CountDownLatch connectedSignal = new CountDownLatch(1);

	private static final Logger log = LoggerFactory.getLogger(ZookeeperRegistry.class);

	@Override
	public void register(URL url) {
		connectServer();

		createNode(url);
	}

	private void createNode(URL url) {
		String data = url.getIp() + ":" + url.getPort();
		try {
			zk.create(Const.ZK_DATA_PATH, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL_SEQUENTIAL);
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
	public void subscribe(URL url) {
		// TODO Auto-generated method stub

	}

}
