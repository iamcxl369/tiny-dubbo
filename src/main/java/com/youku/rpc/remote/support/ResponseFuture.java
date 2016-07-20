package com.youku.rpc.remote.support;

import com.youku.rpc.exception.RpcException;

public interface ResponseFuture {

	Response get() throws RpcException;

	Response get(long timeoutInMillis) throws RpcException;

	boolean isDone();

}
