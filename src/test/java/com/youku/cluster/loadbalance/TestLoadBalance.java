package com.youku.cluster.loadbalance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.invoker.impl.NevermoreInvoker;
import com.youku.rpc.model.User;
import com.youku.rpc.remote.cluster.loadbalance.LoadBalance;
import com.youku.rpc.remote.cluster.loadbalance.impl.ConsistentHashLoadBalance;
import com.youku.rpc.remote.cluster.loadbalance.impl.RandomLoadBalance;
import com.youku.rpc.remote.cluster.loadbalance.impl.RoundRobinLoadBalance;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.URL;

public class TestLoadBalance {

	private List<Invoker> invokers;

	private Request request;

	private Map<String, Integer> count;

	private Map<String, Float> rate;

	private int size = 10000;

	@Before
	public void setup() {
		System.out.println("总共调用" + size + "次");
		count = new HashMap<>();
		rate = new HashMap<>();
		invokers = new ArrayList<>();

		invokers.add(new NevermoreInvoker(new URL("10.10.10.1:8080?weight=200"), null, null, null));
		invokers.add(new NevermoreInvoker(new URL("10.10.10.2:8080?weight=300"), null, null, null));
		invokers.add(new NevermoreInvoker(new URL("10.10.10.3:8080?weight=300"), null, null, null));
		invokers.add(new NevermoreInvoker(new URL("10.10.10.4:8080?weight=200"), null, null, null));

		request = new Request("demo-request");

	}

	@Test
	public void testRandom() {
		execute(new RandomLoadBalance());
	}

	@Test
	public void testRoundRobin() {
		execute(new RoundRobinLoadBalance());
	}

	@Test
	public void testConsistentHash() {
		execute(new ConsistentHashLoadBalance());
	}

	private void execute(LoadBalance loadBalance) {
		for (int i = 0; i < size; i++) {
			request.setArguments(new Object[] { new User(i, "jack") });
			Invoker invoker = loadBalance.select(invokers, request);
			String key = toURL(invoker);

			Integer number = count.get(key);

			if (number == null) {
				number = 0;
			}

			number++;

			count.put(key, number);

			rate.put(key, new BigDecimal(1.0 * number / size).setScale(2, RoundingMode.HALF_UP).floatValue());
		}

		for (Entry<String, Integer> entry : count.entrySet()) {
			System.out.println(entry.getKey() + "被调用" + entry.getValue() + "次---->rate:" + rate.get(entry.getKey()));
		}
	}

	public String toURL(Invoker invoker) {
		return invoker.getURL().toURLString();
	}

	@Test
	public void testDecimal() {
		BigDecimal decimal = new BigDecimal(1.222222).setScale(2, RoundingMode.HALF_UP);

		System.out.println(decimal.floatValue());
	}
}
