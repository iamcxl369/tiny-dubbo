package com.youku.rpc.extension;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import com.youku.rpc.factory.ExtensionFactory;

public class ExtensionLoader {

	private static final String NEVERMORE_DIRECTORY = "META-INF/nevermore/";

	private static final String NEVERMORE_INTERNAL_DIRECTORY = NEVERMORE_DIRECTORY + "internal/";

	private ExtensionFactory extensionFactory = new ExtensionFactory();

	public void load() {
		load(NEVERMORE_INTERNAL_DIRECTORY);
		load(NEVERMORE_DIRECTORY);
	}

	public void load(String location) {
		Enumeration<URL> urls = null;
		try {
			urls = getClassLoader().getResources(NEVERMORE_INTERNAL_DIRECTORY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(urls);
	}

	private ClassLoader getClassLoader() {
		return ExtensionLoader.class.getClassLoader();
	}
}
