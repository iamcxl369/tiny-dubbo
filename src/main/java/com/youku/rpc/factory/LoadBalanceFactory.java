package com.youku.rpc.factory;

import com.youku.rpc.cluster.loadbalance.LoadBalance;
import com.youku.rpc.extension.ExtensionLoader;

public class LoadBalanceFactory {

	public static LoadBalance create(String name) {
		return ExtensionLoader.getExtension(LoadBalance.class, name);
	}

}
