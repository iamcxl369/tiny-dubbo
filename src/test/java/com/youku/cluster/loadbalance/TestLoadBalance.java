package com.youku.cluster.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.youku.rpc.cluster.loadbalance.LoadBalance;
import com.youku.rpc.cluster.loadbalance.RandomLoadBalance;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.invoker.impl.DefaultInvoker;
import com.youku.rpc.net.URL;

public class TestLoadBalance {

	private List<Invoker> invokers;

	private Map<String, Integer> count;

	private int size = 10000;

	@Before
	public void setup() {
		count = new HashMap<>();
		invokers = new ArrayList<>();

		invokers.add(new DefaultInvoker(new URL("10.10.10.1:8080?weight=100"), null, null));
		invokers.add(new DefaultInvoker(new URL("10.10.10.2:8080?weight=300"), null, null));
		invokers.add(new DefaultInvoker(new URL("10.10.10.3:8080?weight=200"), null, null));
		invokers.add(new DefaultInvoker(new URL("10.10.10.4:8080?weight=1"), null, null));

	}

	@Test
	public void testRandom() {
		LoadBalance loadBalance = new RandomLoadBalance();
		execute(loadBalance);
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
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}

	public String toURL(Invoker invoker) {
		return invoker.getURL().toURLString();
	}
}
