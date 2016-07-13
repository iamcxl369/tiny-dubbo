package com.youku.rpc.remote.threadpool;

import java.util.concurrent.Executor;

import com.youku.rpc.remote.URL;

public interface ThreadPool {

	Executor getExecutor(URL url);
}
