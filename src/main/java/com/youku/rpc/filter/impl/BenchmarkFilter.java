package com.youku.rpc.filter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.annotation.Active;
import com.youku.rpc.common.Const;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.filter.Filter;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.URL;

@Active
public class BenchmarkFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(BenchmarkFilter.class);

	@Override
	public Response invoke(Invoker invoker, Request request) throws RpcException {
		URL url = invoker.getURL();
		if (url.getBooleanParam(Const.BENCHMARK, Const.DEFAULT_BENCHMARK)) {
			log.debug("进入benchmark filter");
			long start = System.currentTimeMillis();

			Response response = invoker.invoke(request);

			long end = System.currentTimeMillis();

			log.info("================ cost {} ms", end - start);
			return response;
		} else {
			return invoker.invoke(request);
		}

	}
}
