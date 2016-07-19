package com.youku.rpc.remote;

import java.util.concurrent.Future;

/**
 * rpc调用环境
 * 
 * @author loda
 *
 */
public class RpcContext {

	private static ThreadLocal<RpcContext> context = new ThreadLocal<RpcContext>() {

		@Override
		protected RpcContext initialValue() {
			return new RpcContext();
		}
	};

	private Future<?> future;

	public static RpcContext getContext() {
		return context.get();
	}

	@SuppressWarnings("unchecked")
	public <V> Future<V> getFuture() {
		return (Future<V>) future;
	}

	public <V> void setFuture(Future<V> future) {
		this.future = future;
	}

}
