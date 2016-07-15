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

	public static RpcContext getContext() {
		return context.get();
	}

	public <V> Future<V> getFuture() {
		return null;
	}

}
