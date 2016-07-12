package com.youku.extension;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.youku.rpc.extension.Extension;
import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.factory.ClusterFactory;
import com.youku.rpc.factory.LoadBalanceFactory;
import com.youku.rpc.filter.Filter;

public class TestExtension {

	@Before
	public void load() {
		new ExtensionLoader();
	}

	@Test
	public void testGetCluster() {
		System.out.println(ClusterFactory.create("failover"));
	}

	@Test
	public void testGetLoadBalance() {
		System.out.println(LoadBalanceFactory.create("random"));
	}

	@Test
	public void testGetExtensions() {
		Map<String, Extension> extensions = ExtensionLoader.getExtensions();

		for (Entry<String, Extension> entry : extensions.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}

	@Test
	public void testGetFilterExtensions() {
		List<Filter> filters = ExtensionLoader.getExtensions(Filter.class);

		for (Filter filter : filters) {
			System.out.println(filter);
		}
	}

	@Test
	public void testGetActiveFilterExtensions() {
		List<Filter> filters = ExtensionLoader.getActiveExtensions(Filter.class);

		for (Filter filter : filters) {
			System.out.println(filter);
		}
	}
}
