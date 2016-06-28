package com.youku.rpc.cluster;

import java.util.List;

import com.youku.rpc.cluster.loadbalance.LoadBalance;
import com.youku.rpc.invoker.Invoker;

public class Directory {

	private List<Invoker> invokers;

	private int retryTimes;

	private LoadBalance loadBalance;

	public Directory(List<Invoker> invokers, int retryTimes, LoadBalance loadBalance) {
		super();
		this.invokers = invokers;
		this.retryTimes = retryTimes;
		this.loadBalance = loadBalance;
	}

	public List<Invoker> getInvokers() {
		return invokers;
	}

	public int getRetryTimes() {
		return retryTimes;
	}
	
	public LoadBalance getLoadBalance(){
		return loadBalance;
	}

}
