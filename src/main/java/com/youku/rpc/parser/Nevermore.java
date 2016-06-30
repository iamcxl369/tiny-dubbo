package com.youku.rpc.parser;

import com.youku.rpc.extension.ExtensionLoader;

public class Nevermore {

	private XmlParser parser;

	private ExtensionLoader loader;

	public Nevermore(String location) {
		loader = new ExtensionLoader();
		parser = new XmlParser(location);
	}

	public void start() {
		parser.execute();
	}

	public void close() {
		loader.close();
		parser.close();
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName) {
		return (T) parser.getContext().get(beanName);
	}
}
