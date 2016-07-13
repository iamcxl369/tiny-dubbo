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

	String WEIGHT = "weight";

	String SERIALIZER = "serializer";

	String PROTOCOL = "protocol";

	short MAGIC = 0x10da;

	byte REQUEST = 0;

	byte RESPONSE = 1;

	String INTERFACE = "interface";

	String REGISTRY_PROTOCOL = "registry";

	String CLUSTER = "cluster";

	String LOADBALANCE = "loadbalance";

	String RETRY_TIMES = "retry_times";

	String CORE_THREADS = "corethreads";

	String THREADS = "threads";

	String ALIVE = "alive";
}
