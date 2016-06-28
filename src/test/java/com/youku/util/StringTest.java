package com.youku.util;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringTest {

	@Test
	public void testSplit(){
		String[] arr=StringUtils.split("name",'&');
		
		System.out.println(Arrays.toString(arr));
	}
}
