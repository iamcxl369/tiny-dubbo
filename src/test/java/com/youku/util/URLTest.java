package com.youku.util;

import org.junit.Test;

import com.youku.rpc.remote.URL;

public class URLTest {

	@Test
	public void testURL() {
		String urlString = "10.10.23.22:8080?name=jack&age=22&sex=male";
		URL url = new URL(urlString);

		System.out.println(url);

		System.out.println(url.toURLString());
	}
}
