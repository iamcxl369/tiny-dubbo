package com.youku.rpc.remote.protocol.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.filter.Filter;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.protocol.Protocol;

public class ProtocolFilterWrapper implements Protocol {

	private Protocol protocol;

	public ProtocolFilterWrapper(Protocol protocol) {
		Assert.notNull(protocol);
		this.protocol = protocol;
	}

	@Override
	public void export(Invoker invoker) {
		protocol.export(invoker);
	}

	@Override
	public Invoker refer(Class<?> interfaceClass, URL url) {
		if (protocol instanceof RegistryProtocol) {
			return protocol.refer(interfaceClass, url);
		} else {
			return buildFilterChain(protocol.refer(interfaceClass, url));
		}
	}

	private Invoker buildFilterChain(final Invoker invoker) {

		List<Filter> filters = ExtensionLoader.getActiveExtensions(Filter.class);

		Invoker last = invoker;
		// 形成filter1->filter2->...->last filter->invoker的调用链，并封装到last对象中
		for (int i = filters.size() - 1; i >= 0; i--) {
			Filter filter = filters.get(i);
			final Filter next = filter;
			last = new Invoker() {

				@Override
				public Response invoke(Request request) throws RpcException {
					return next.invoke(invoker, request);
				}

				@Override
				public URL getURL() {
					return invoker.getURL();
				}

				@Override
				public Object getTargetEntity() {
					return invoker.getTargetEntity();
				}

				@Override
				public ClassLoader getInterfaceClassLoader() {
					return invoker.getInterfaceClassLoader();
				}

				@Override
				public Class<?> getInterfaceClass() {
					return invoker.getInterfaceClass();
				}
			};

		}
		return last;
	}

	@Override
	public String toString() {
		return "ProtocolFilterWrapper [protocol=" + protocol + "]";
	}

}
