package com.youku.rpc.cluster.loadbalance;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.invoker.Invoker;

public class RandomLoadBalance implements LoadBalance {

	private final Logger log = LoggerFactory.getLogger(RandomLoadBalance.class);

	private final Random random = new Random();

	@Override
	public Invoker select(List<Invoker> invokers) {
		log.info("采用随机负载均衡措施");
		int selectIndex = random.nextInt(invokers.size());
		return invokers.get(selectIndex);
	}

}
