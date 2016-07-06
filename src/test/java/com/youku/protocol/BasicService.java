package com.youku.protocol;

public class BasicService implements BasicAPI {
	private String _greeting = "Hello, world";

	public void setGreeting(String greeting) {
		_greeting = greeting;
	}

	@Override
	public String hello() {
		System.out.println(_greeting + " in server");
		return _greeting;
	}

}
