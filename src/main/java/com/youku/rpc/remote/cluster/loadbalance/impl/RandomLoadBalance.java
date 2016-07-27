package com.youku.rpc.remote.cluster.loadbalance.impl;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.support.Request;

/**
 * 随机算法
 * 
 * @author loda
 *
 */
public class RandomLoadBalance extends AbstractLoadBalance {

	private static final Logger log = LoggerFactory.getLogger(RandomLoadBalance.class);

	private final Random random = new Random();

	@Override
	public Invoker doSelect(List<Invoker> invokers, Request request) {
		log.debug("采用随机负载均衡措施");

		int totalWeight = 0;

		for (Invoker invoker : invokers) {
			totalWeight += getWeight(invoker);
		}

		int weight = random.nextInt(totalWeight);

		int selectIndex;
		for (selectIndex = 0; selectIndex < invokers.size(); selectIndex++) {
			weight -= getWeight(invokers.get(selectIndex));

			if (weight < 0) {
				break;
			}
		}

		return invokers.get(selectIndex);
	}

}
