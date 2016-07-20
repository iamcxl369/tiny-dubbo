package com.youku.rpc.remote.threadpool.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.youku.rpc.common.Const;
import com.youku.rpc.remote.support.URL;
import com.youku.rpc.remote.threadpool.ThreadPool;

public class CachedThreadPool implements ThreadPool {

	@Override
	public Executor getExecutor(URL url) {
		int coreThreads = url.getIntParam(Const.CORE_THREADS_KEY, 0);
		int maxThreads = url.getIntParam(Const.THREADS_KEY, Integer.MAX_VALUE);
		long alive = url.getLongParam(Const.ALIVE_KEY, 60000L);
		return new ThreadPoolExecutor(coreThreads, maxThreads, alive, TimeUnit.MILLISECONDS,
				new SynchronousQueue<Runnable>());
	}

}
