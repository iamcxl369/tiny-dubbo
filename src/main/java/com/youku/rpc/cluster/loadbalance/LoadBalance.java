package com.youku.rpc.cluster.loadbalance;

import java.util.List;

import com.youku.rpc.invoker.Invoker;

public interface LoadBalance {

	Invoker select(List<Invoker> invokers);
}
