package com.youku.cluster.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.invoker.impl.NevermoreInvoker;
import com.youku.rpc.remote.cluster.loadbalance.LoadBalance;
import com.youku.rpc.remote.cluster.loadbalance.impl.RandomLoadBalance;
import com.youku.rpc.remote.cluster.loadbalance.impl.RoundRobinLoadBalance;
import com.youku.rpc.remote.support.URL;

public class TestLoadBalance {

	private List<Invoker> invokers;

	private Map<String, Integer> count;

	private int size = 10000;

	@Before
	public void setup() {
		System.out.println("总共调用" + size + "次");
		count = new HashMap<>();
		invokers = new ArrayList<>();

		invokers.add(new NevermoreInvoker(new URL("10.10.10.1:8080?weight=200"), null, null, null));
		invokers.add(new NevermoreInvoker(new URL("10.10.10.2:8080?weight=300"), null, null, null));
		invokers.add(new NevermoreInvoker(new URL("10.10.10.3:8080?weight=300"), null, null, null));
		invokers.add(new NevermoreInvoker(new URL("10.10.10.4:8080?weight=200"), null, null, null));

	}

	@Test
	public void testRandom() {
		execute(new RandomLoadBalance());
	}

	@Test
	public void testRoundRobin() {
		execute(new RoundRobinLoadBalance());
	}

	private void execute(LoadBalance loadBalance) {
		for (int i = 0; i < size; i++) {
			Invoker invoker = loadBalance.select(invokers);
			String key = toURL(invoker);

			Integer number = count.get(key);

			if (number == null) {
				number = 0;
			}

			number++;

			count.put(key, number);
		}

		for (Entry<String, Integer> entry : count.entrySet()) {
			System.out.println(entry.getKey() + "被调用" + entry.getValue() + "次");
		}
	}

	public String toURL(Invoker invoker) {
		return invoker.getURL().toURLString();
	}
}
