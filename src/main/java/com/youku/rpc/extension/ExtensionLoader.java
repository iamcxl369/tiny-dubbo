package com.youku.rpc.extension;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.youku.rpc.common.ReflectUtils;
import com.youku.rpc.factory.ExtensionFactory;

public class ExtensionLoader {

	private static final String NEVERMORE_DIRECTORY = "META-INF/nevermore/";

	private static final String NEVERMORE_INTERNAL_DIRECTORY = NEVERMORE_DIRECTORY + "internal/";

	private static ExtensionFactory extensionFactory = new ExtensionFactory();

	private static final Logger log = LoggerFactory.getLogger(ExtensionLoader.class);

	public ExtensionLoader() {
		load(NEVERMORE_INTERNAL_DIRECTORY);
		load(NEVERMORE_DIRECTORY);
	}

	public void close() {
		extensionFactory = null;
	}

	private void load(String location) {
		log.info("加载{}中扩展信息", location);
		Enumeration<URL> urls = null;
		try {
			urls = getClassLoader().getResources(location);
		} catch (IOException e) {
			urls = Collections.emptyEnumeration();
		}

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			File dir = new File(url.getFile());

			for (File file : dir.listFiles()) {
				String interfaceName = file.getName();

				List<String> lines = null;
				try {
					lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
				} catch (IOException e) {
					lines = Collections.emptyList();
				}

				Map<String, Class<?>> normalClasses = new LinkedHashMap<>(lines.size());
				List<Class<?>> wrapperClasses = new ArrayList<>(lines.size());

				for (String line : lines) {
					if (StringUtils.isNotBlank(line)) {
						String[] arr = StringUtils.split(line, '=');

						Assert.isTrue(arr.length == 2, "配置文件的格式为xxx=yyyy");
						String name = arr[0];
						String className = arr[1];

						Class<?> targetClass = ReflectUtils.forName(className);

						try {
							targetClass.getConstructor(new Class<?>[] {});
							normalClasses.put(name, targetClass);
						} catch (NoSuchMethodException e) {
							wrapperClasses.add(targetClass);
						} catch (SecurityException e) {
							throw new RuntimeException(e);
						}

					}
				}

				for (Entry<String, Class<?>> entry : normalClasses.entrySet()) {
					extensionFactory.addExtension(interfaceName, entry.getKey(), entry.getValue(),
							wrapperClasses.toArray(new Class<?>[] {}));
				}
			}
		}

	}

	private ClassLoader getClassLoader() {
		return ExtensionLoader.class.getClassLoader();
	}

	public static <T> T getExtension(Class<T> targetClass, String name) {
		return extensionFactory.getExtension(targetClass, name);
	}

	public static <T> List<T> getExtensions(Class<T> targetClass) {
		return extensionFactory.getExtensions(targetClass);
	}

	public static <T> List<T> getActiveExtensions(Class<T> targetClass) {
		return extensionFactory.getActiveExtensions(targetClass);
	}

	public static Map<String, Extension> getExtensions() {
		return extensionFactory.getExtensions();
	}
}
