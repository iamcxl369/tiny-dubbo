package com.youku.rpc.remote.support;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.exception.TimeoutException;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;

public class DefaultFuture implements ResponseFuture {

	private volatile Response response;

	private final long timeout;

	private final Lock lock = new ReentrantLock();

	private final Condition condition = lock.newCondition();

	private static ConcurrentMap<String, DefaultFuture> futureMap = new ConcurrentHashMap<>();

	public DefaultFuture(long timeout, Request request) {
		super();
		this.timeout = timeout;
		futureMap.put(request.getId(), this);
	}

	@Override
	public Response get() throws RpcException {
		return get(timeout);
	}

	@Override
	public Response get(long timeout) throws RpcException {
		if (!isDone()) {
			long start = System.currentTimeMillis();
			lock.lock();
			try {
				while (!isDone()) {
					condition.await(timeout, TimeUnit.MILLISECONDS);
					if (isDone() || (System.currentTimeMillis() - start > timeout)) {
						break;
					}
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				lock.unlock();
			}
		}

		if (!isDone()) {
			throw new TimeoutException("获取response超过了指定时间" + timeout + "毫秒");
		} else {
			return response;
		}
	}

	@Override
	public boolean isDone() {
		return response != null;
	}

	public void doReceived(Response response) {
		lock.lock();
		try {
			this.response = response;
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

	public static void received(Response response) {
		DefaultFuture future = futureMap.remove(response.getId());
		if (future != null) {
			future.doReceived(response);
		}
	}

}
