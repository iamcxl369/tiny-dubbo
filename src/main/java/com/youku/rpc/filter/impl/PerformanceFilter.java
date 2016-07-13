package com.youku.rpc.filter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.annotation.Active;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.filter.Filter;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;

@Active
public class PerformanceFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(PerformanceFilter.class);

	@Override
	public Response invoke(Invoker invoker, Request request) throws RpcException {
		log.info("进入performance filter");
		long start = System.currentTimeMillis();

		Response response = invoker.invoke(request);

		long end = System.currentTimeMillis();

		log.info("================ cost {} ms", end - start);

		return response;
	}
}
