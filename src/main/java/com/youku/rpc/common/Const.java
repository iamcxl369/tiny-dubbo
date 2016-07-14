package com.youku.rpc.common;

public interface Const {

	// 连接超时时间（秒）
	long CONNECT_TIME_OUT = 3;

	// rpc请求时间（秒）
	long TIME_OUT = 1;

	int ZK_SESSION_TIMEOUT = 5000;

	String ZK_REGISTRY_PATH = "/registry";

	String REGISTRY_SEPARATOR = "://";

	int DEFAULT_WEIGHT = 100;

	String PROTOCOL = "protocol";

	short MAGIC = 0x10da;

	byte REQUEST = 0;

	byte RESPONSE = 1;

	String REGISTRY_PROTOCOL = "registry";

	String WEIGHT = "weight";

	String SERIALIZER = "serializer";

	String ACCESSLOG = "accesslog";

	int DEFAULT_RETRY_TIMES = 3;

	String DEFAULT_LOAD_BALANCE = "random";

	String DEFAULT_CLUSTER = "failover";

	String INTERFACE = "interface";

	String CLUSTER = "cluster";

	String LOADBALANCE = "loadbalance";

	String RETRY_TIMES = "retry_times";

	String CORE_THREADS = "corethreads";

	String THREADS = "threads";

	String ALIVE = "alive";

	String BENCHMARK = "benchmark";

	String ID = "id";

	boolean DEFAULT_BENCHMARK = true;
}
