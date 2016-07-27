package com.youku.rpc.remote.cluster.loadbalance.impl;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.youku.rpc.common.Const;
import com.youku.rpc.common.hash.HashFunction;
import com.youku.rpc.common.hash.impl.MD5HashFunction;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.URL;

public class ConsistentHashLoadBalance extends AbstractLoadBalance {

	@Override
	protected Invoker doSelect(List<Invoker> invokers, Request request) {
		if (invokers.isEmpty()) {
			return null;
		}

		URL url = invokers.get(0).getURL();
		ConsistentHash consistentHash = new ConsistentHash(
				url.getIntParam(Const.HASH_NODES_KEY, Const.DEFAULT_HASH_NODES), invokers);

		return consistentHash.get(toKey(request.getArguments()));
	}

	private String toKey(Object[] arguments) {
		StringBuilder builder = new StringBuilder();
		for (Object arg : arguments) {
			builder.append(arg);
		}
		return builder.toString();
	}

	public final static class ConsistentHash {

		private HashFunction function = new MD5HashFunction();

		private int numberOfReplicas;

		private SortedMap<Integer, Invoker> circle = new TreeMap<>();

		public ConsistentHash(int numberOfReplicas, List<Invoker> invokers) {
			super();
			this.numberOfReplicas = numberOfReplicas;

		}

		public void add(Invoker invoker) {
			for (int i = 0; i < numberOfReplicas; i++) {
				int hash = function.hash(invoker.getURL().getIp() + "#" + i);
				circle.put(hash, invoker);
			}
		}

		public void remove(Invoker invoker) {
			for (int i = 0; i < numberOfReplicas; i++) {
				int hash = function.hash(invoker.getURL().getIp() + "#" + i);
				circle.remove(hash);
			}
		}

		public Invoker get(Object key) {
			if (circle.isEmpty()) {
				return null;
			} else {
				int hash = function.hash(key);
				if (!circle.containsKey(hash)) {
					SortedMap<Integer, Invoker> candidates = circle.tailMap(hash);
					hash = candidates.isEmpty() ? circle.firstKey() : candidates.firstKey();
				}
				return circle.get(hash);
			}
		}

	}

}
