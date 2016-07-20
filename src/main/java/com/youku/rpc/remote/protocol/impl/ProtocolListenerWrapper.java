package com.youku.rpc.remote.protocol.impl;

import org.springframework.util.Assert;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.protocol.Protocol;
import com.youku.rpc.remote.support.URL;

public class ProtocolListenerWrapper implements Protocol {

	private Protocol protocol;

	public ProtocolListenerWrapper(Protocol protocol) {
		Assert.notNull(protocol);
		this.protocol = protocol;
	}

	@Override
	public void export(Invoker invoker) {
		protocol.export(invoker);
	}

	@Override
	public Invoker refer(Class<?> interfaceClass, URL url) {
		return protocol.refer(interfaceClass, url);
	}

	@Override
	public String toString() {
		return "ProtocolListenerWrapper [protocol=" + protocol + "]";
	}

}
