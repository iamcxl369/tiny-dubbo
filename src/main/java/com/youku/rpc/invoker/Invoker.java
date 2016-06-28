package com.youku.rpc.invoker;

import com.youku.rpc.client.Request;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.net.URL;
import com.youku.rpc.server.Response;

public interface Invoker {

	Class<?> getInterfaceClass();

	ClassLoader getInterfaceClassLoader();

	Response invoke(Request request) throws RpcException;

	URL getURL();
}
