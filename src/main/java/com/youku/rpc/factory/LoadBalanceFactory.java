package com.youku.rpc.factory;

import com.youku.rpc.cluster.loadbalance.LoadBalance;
import com.youku.rpc.cluster.loadbalance.impl.RandomLoadBalance;

public class LoadBalanceFactory {

	public static LoadBalance create(String loadBalanceName) {
		// TODO 目前只支持random负载均衡措施
		return new RandomLoadBalance();
	}

}
