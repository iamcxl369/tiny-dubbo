package com.youku.rpc.cluster.loadbalance;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.invoker.Invoker;

public class RoundRobinLoadBalance extends AbstractLoadBalance {
	private static final Logger log = LoggerFactory.getLogger(RoundRobinLoadBalance.class);

	private int index = -1;

	private int cardinalWeight;

	@Override
	protected Invoker doSelect(List<Invoker> invokers) {
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
			invokers.get(index);
		}
		throw new RuntimeException("找不到可以调度的提供者");
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
			throw new IllegalArgumentException("不存在任何数据信息，没有最大公约数");
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

	public static void main(String[] args) {
		int r = new RoundRobinLoadBalance().gcd(120, 96);
		System.out.println(r);
	}
}
