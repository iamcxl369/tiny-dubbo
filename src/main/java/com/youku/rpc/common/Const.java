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

	Object PROTOCOL = "protocol";
}
