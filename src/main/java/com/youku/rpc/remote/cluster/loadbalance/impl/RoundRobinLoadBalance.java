package com.youku.rpc.remote.cluster.loadbalance.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.support.Request;

/**
 * 加权轮询算法
 * 
 * 由于采用轮询，如果某一台服务很慢，那么轮询其他的时候很快就执行完成，轮询到这一台的时候就卡住了，久而久之，所有堆积大量请求
 * 
 * @author loda
 *
 */
public class RoundRobinLoadBalance extends AbstractLoadBalance {
	private static final Logger log = LoggerFactory.getLogger(RoundRobinLoadBalance.class);

	private int index = -1;

	private int cardinalWeight;

	@Override
	protected Invoker doSelect(List<Invoker> invokers, Request request) {
		log.debug("采用权重轮询负载均衡措施");

		index = (index + 1) % invokers.size();

		if (index == 0) {
			cardinalWeight -= gcdWeight(invokers);

			if (cardinalWeight <= 0) {
				cardinalWeight = maxWeight(invokers);
			}
		}

		Invoker current = invokers.get(index);
		if (getWeight(current) >= cardinalWeight) {
			return current;
		} else {
			return doSelect(invokers, request);
		}
	}

	private int maxWeight(List<Invoker> invokers) {
		int[] weights = fillWeightArr(invokers);
		int max = Integer.MIN_VALUE;
		for (int weight : weights) {
			if (weight > max) {
				max = weight;
			}
		}
		return max;
	}

	private int gcdWeight(List<Invoker> invokers) {
		int[] weights = fillWeightArr(invokers);
		return gcd(weights);
	}

	private int[] fillWeightArr(List<Invoker> invokers) {
		int[] weights = new int[invokers.size()];
		for (int i = 0; i < invokers.size(); i++) {
			weights[i] = getWeight(invokers.get(i));
		}
		return weights;
	}

	private int gcd(int[] weights) {
		if (weights == null || weights.length == 0) {
			throw new IllegalArgumentException("不存在任何提供者信息，没有最大公约数");
		}

		if (weights.length == 1) {
			return weights[0];
		} else if (weights.length == 2) {
			return gcd(weights[0], weights[1]);
		} else {
			int base = gcd(weights[0], weights[1]);

			for (int i = 2; i < weights.length; i++) {
				base = gcd(weights[i], base);
			}

			return base;
		}

	}

	private int gcd(int a, int b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

}
