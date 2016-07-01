package com.youku.rpc.remote.server;

import java.util.HashMap;
import java.util.Map;

public class TypeObjectMapper {

	private static Map<Class<?>, Object> mapper = new HashMap<>();

	public static void binding(Class<?> targetClass, Object object) {
		mapper.put(targetClass, object);
	}

	public static Object get(Class<?> targetClass) {
		return mapper.get(targetClass);
	}

	public static Map<Class<?>, Object> getMapper() {
		return mapper;
	}

	public static void setMapper(Map<Class<?>, Object> mapper) {
		TypeObjectMapper.mapper = mapper;
	}
}
