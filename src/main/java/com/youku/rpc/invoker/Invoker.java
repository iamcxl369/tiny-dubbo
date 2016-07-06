package com.youku.rpc.invoker;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.URL;

public interface Invoker {

	Class<?> getInterfaceClass();

	Object getTargetEntity();

	ClassLoader getInterfaceClassLoader();

	Response invoke(Request request) throws RpcException;

	URL getURL();
}
