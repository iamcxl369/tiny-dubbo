package com.youku.rpc.invoker;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.client.Request;
import com.youku.rpc.remote.server.Response;

public interface Invoker {

	Class<?> getInterfaceClass();

	ClassLoader getInterfaceClassLoader();

	Response invoke(Request request) throws RpcException;

	URL getURL();
}
