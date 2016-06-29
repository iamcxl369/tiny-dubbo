package com.youku.rpc.extension;

import java.util.HashMap;
import java.util.Map;

public class Extension {

	private Map<String, Object> beans = new HashMap<>();

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
}
