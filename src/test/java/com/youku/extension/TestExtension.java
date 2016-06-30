package com.youku.extension;

import org.junit.Before;
import org.junit.Test;

import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.factory.ClusterFactory;
import com.youku.rpc.factory.LoadBalanceFactory;

public class TestExtension{
	
	@Before
	public void load(){
		new ExtensionLoader().load();
	}

	@Test
	public void testGetCluster(){
		System.out.println(ClusterFactory.create("failover"));
	}
	
	@Test
	public void testGetLoadBalance(){
		System.out.println(LoadBalanceFactory.create("random"));
	}
}
