package com.youku.rpc.remote.threadpool.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.youku.rpc.common.Const;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.threadpool.ThreadPool;

public class FixedThreadPool implements ThreadPool {

	@Override
	public Executor getExecutor(URL url) {
		int threads = url.getIntParam(Const.THREADS, 200);
		return new ThreadPoolExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

}
