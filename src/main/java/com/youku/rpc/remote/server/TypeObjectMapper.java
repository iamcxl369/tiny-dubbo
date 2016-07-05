package com.youku.rpc.remote.server;

import java.util.HashMap;
import java.util.Map;

public class TypeObjectMapper {

	private static Map<String, Object> mapper = new HashMap<>();

	public static void binding(String className, Object object) {
		mapper.put(className, object);
	}

	public static Object get(String className) {
		return mapper.get(className);
	}

	public static Map<String, Object> getMapper() {
		return mapper;
	}

	public static void setMapper(Map<String, Object> mapper) {
		TypeObjectMapper.mapper = mapper;
	}

}
