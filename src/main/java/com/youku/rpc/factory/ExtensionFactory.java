package com.youku.rpc.factory;

import java.util.HashMap;
import java.util.Map;

public class ExtensionFactory {

	private Map<String, Object> extension = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T getExtension(String name) {
		return (T) extension.get(name);
	}
}
