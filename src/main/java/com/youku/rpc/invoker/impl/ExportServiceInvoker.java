package com.youku.rpc.invoker.impl;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.Response;
import com.youku.rpc.remote.support.URL;

public class ExportServiceInvoker extends AbstractInvoker {

	public ExportServiceInvoker(URL url, Object targetEntity, Class<?> interfaceClass) {
		super(url, targetEntity, interfaceClass);
	}

	@Override
	public Response invoke(Request request) throws RpcException {
		throw new RuntimeException(this.getClass().getName() + "的invoke方法不能被调用");
	}

}
