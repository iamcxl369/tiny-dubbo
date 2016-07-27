package com.youku.rpc.invoker.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.cluster.Directory;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.Response;

public class FailoverClusterInvoker extends AbstractClusterInvoker {

	private static final Logger log = LoggerFactory.getLogger(FailoverClusterInvoker.class);

	public FailoverClusterInvoker(Directory directory) {
		super(directory);
	}

	@Override
	public Response invoke(Request request) throws RpcException {
		log.debug("采用failover集群容错方案");
		int retryTimes = directory.getRetryTimes();
		retryTimes++;

		for (int i = 0; i < retryTimes; i++) {
			try {
				Invoker invoker = select(directory.getInvokers(), request);
				return invoker.invoke(request);
			} catch (RpcException e) {
				log.warn("远程调用失败，目前已经重试了{}次", i);
			}
		}
		throw new RpcException("远程调用失败，已经重试" + directory.getRetryTimes() + "次");
	}

}
