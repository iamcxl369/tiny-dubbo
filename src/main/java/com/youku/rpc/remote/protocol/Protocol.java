package com.youku.rpc.remote.protocol;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.support.URL;

public interface Protocol {

	void export(Invoker invoker);

	Invoker refer(Class<?> interfaceClass, URL url);
}
