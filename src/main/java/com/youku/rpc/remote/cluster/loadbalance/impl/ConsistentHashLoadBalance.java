package com.youku.rpc.remote.cluster.loadbalance.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.youku.rpc.common.Const;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.URL;

/**
 * ketama算法实现的一致性hash
 * 
 * @author loda
 *
 */
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

		private int numberOfReplicas;

		private SortedMap<Long, Invoker> circle = new TreeMap<>();

		public ConsistentHash(int numberOfReplicas, List<Invoker> invokers) {
			this.numberOfReplicas = numberOfReplicas;
			for (Invoker invoker : invokers) {
				add(invoker);
			}
		}

		public void add(Invoker invoker) {
			for (int i = 0; i < numberOfReplicas / 4; i++) {
				String key = invoker.getURL().getIp() + "#" + i;
				byte[] digest = md5(key);
				for (int j = 0; j < 4; j++) {
					long hash = hash(digest, j);
					circle.put(hash, invoker);
				}
			}
		}

		private long hash(byte[] digest, int offset) {
			// 分散,截取32位
			Long k = ((long) (digest[3 + offset * 4] & 0xFF) << 24) //
					| ((long) (digest[2 + offset * 4] & 0xFF) << 16)//
					| ((long) (digest[1 + offset * 4] & 0xFF) << 8)//
					| ((long) (digest[0 + offset * 4] & 0xFF))//
							& 0xFFFFFFFFL;
			return k;
		}

		private byte[] md5(String key) {
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException("no md5 algorythm found", e);
			}
			md5.reset();
			md5.update(key.getBytes());
			return md5.digest();
		}

		public Invoker get(Object key) {
			if (circle.isEmpty()) {
				return null;
			} else {
				long hash = hash(md5(key.toString()), 0);
				if (!circle.containsKey(hash)) {
					SortedMap<Long, Invoker> candidates = circle.tailMap(hash);
					hash = candidates.isEmpty() ? circle.firstKey() : candidates.firstKey();
				}
				return circle.get(hash);
			}
		}

	}

}
