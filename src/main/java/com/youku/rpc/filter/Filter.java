package com.youku.rpc.filter;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.Response;

public interface Filter {

	Response invoke(Invoker invoker, Request request) throws RpcException;
}
