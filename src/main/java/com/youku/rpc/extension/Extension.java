package com.youku.rpc.extension;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Extension {

	private Map<String, Object> beans = new LinkedHashMap<>();

	public Map<String, Object> getBeans() {
		return beans;
	}

	public void setBeans(Map<String, Object> beans) {
		this.beans = beans;
	}

	public void addBean(String key, Object value) {
		beans.put(key, value);
	}

	public Object getBean(String key) {
		return beans.get(key);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, Object> entry : beans.entrySet()) {
			builder.append("\tbeans=[").append(entry.getKey()).append("=").append(entry.getValue()).append("]\n");
		}
		return builder.toString();
	}
}
