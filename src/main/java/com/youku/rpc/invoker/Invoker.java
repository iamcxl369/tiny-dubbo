package com.youku.rpc.invoker;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.Response;
import com.youku.rpc.remote.support.URL;

public interface Invoker {

	Class<?> getInterfaceClass();

	Object getTargetEntity();

	ClassLoader getInterfaceClassLoader();

	Response invoke(Request request) throws RpcException;

	URL getURL();
}
