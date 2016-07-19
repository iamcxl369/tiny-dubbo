package com.youku.rpc.remote.support;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.exception.TimeoutException;
import com.youku.rpc.remote.Response;

public class DefaultFuture implements ResponseFuture {

	private volatile Response response;

	private long timeout;

	private Lock lock = new ReentrantLock();

	private Condition condition = lock.newCondition();

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

}
