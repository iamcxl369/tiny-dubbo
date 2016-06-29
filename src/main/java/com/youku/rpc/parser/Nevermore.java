package com.youku.rpc.parser;

public class Nevermore {

	private XmlParser parser;

	public Nevermore(String location) {
		parser = new XmlParser();
		parser.parse(location);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName) {
		return (T) parser.getContext().get(beanName);
	}
}
