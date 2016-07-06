package com.youku.protocol;

import java.net.MalformedURLException;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;

public class TestHessian {

	@Test
	public void testInvoke() throws MalformedURLException {
		HessianProxyFactory factory = new HessianProxyFactory();
		BasicAPI basic = (BasicAPI) factory.create(BasicAPI.class, "http://localhost:8080");
		System.out.println(basic.hello());
	}
}
