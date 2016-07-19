package com.youku.rpc.remote.support;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.Response;

public interface ResponseFuture {

	Response get() throws RpcException;

	Response get(long timeoutInMillis) throws RpcException;

	boolean isDone();

}
