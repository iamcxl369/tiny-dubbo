package com.youku.rpc.filter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.annotation.Active;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.filter.Filter;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.Response;

@Active
public class MonitorFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(MonitorFilter.class);

	@Override
	public Response invoke(Invoker invoker, Request request) throws RpcException {
		log.debug("进入monitor filter");
		return invoker.invoke(request);

	}

}
