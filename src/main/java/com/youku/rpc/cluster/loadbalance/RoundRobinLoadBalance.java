package com.youku.rpc.cluster.loadbalance;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.invoker.Invoker;

public class RoundRobinLoadBalance extends AbstractLoadBalance {
	private static final Logger log = LoggerFactory.getLogger(RoundRobinLoadBalance.class);

	@Override
	protected Invoker doSelect(List<Invoker> invokers) {
		// TODO Auto-generated method stub
		log.debug("采用权重轮询负载均衡措施");
		return null;
	}

}
