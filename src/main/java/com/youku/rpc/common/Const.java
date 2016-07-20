package com.youku.rpc.common;

public interface Const {

	// 连接超时时间（毫秒）
	int CONNECT_TIMEOUT = 3000;

	// rpc请求超时时间（毫秒）
	long DEFAULT_TIMEOUT = 1000;

	int ZK_SESSION_TIMEOUT = 5000;

	String ZK_REGISTRY_PATH = "/registry";

	String REGISTRY_SEPARATOR = "://";

	int DEFAULT_WEIGHT = 100;

	String PROTOCOL_KEY = "protocol";

	short MAGIC = 0x10da;

	String REGISTRY_PROTOCOL_KEY = "registry";

	String WEIGHT_KEY = "weight";

	String SERIALIZER_KEY = "serializer";

	String ACCESSLOG_KEY = "accesslog";

	int DEFAULT_RETRY_TIMES = 3;

	String DEFAULT_LOAD_BALANCE = "random";

	String DEFAULT_CLUSTER = "failover";

	String INTERFACE_KEY = "interface";

	String CLUSTER_KEY = "cluster";

	String TIMEOUT_KEY = "timeout";

	String LOADBALANCE_KEY = "loadbalance";

	String RETRY_TIMES_KEY = "retry_times";

	String CORE_THREADS_KEY = "corethreads";

	String THREADS_KEY = "threads";

	String ALIVE_KEY = "alive";

	String BENCHMARK_KEY = "benchmark";

	String ID_KEY = "id";

	String ASYNC_KEY = "async";

	boolean DEFAULT_BENCHMARK = true;

	boolean DEFAULT_ASYNC = false;

}
