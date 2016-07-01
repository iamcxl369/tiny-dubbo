package com.youku.rpc.factory;

import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.remote.cluster.loadbalance.LoadBalance;

public class LoadBalanceFactory {

	public static LoadBalance create(String name) {
		return ExtensionLoader.getExtension(LoadBalance.class, name);
	}

}
