package com.youku.rpc.remote.cluster.loadbalance;

import java.util.List;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.support.Request;

public interface LoadBalance {

	Invoker select(List<Invoker> invokers, Request request);
}
