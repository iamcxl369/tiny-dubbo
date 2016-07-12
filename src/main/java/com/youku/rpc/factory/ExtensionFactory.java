package com.youku.rpc.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

import com.youku.rpc.annotation.Active;
import com.youku.rpc.common.ReflectUtils;
import com.youku.rpc.extension.ActiveComparator;
import com.youku.rpc.extension.Extension;

public class ExtensionFactory {

	private Map<String, Extension> extensions = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> T getExtension(Class<T> targetClass, String name) {
		Extension extension = extensions.get(targetClass.getName());
		Assert.notNull(extension, "不存在接口" + targetClass.getName() + "的扩展");
		T instance = (T) extension.getBean(name);
		Assert.notNull(instance, "接口" + targetClass.getName() + "下不存在名为" + name + "的扩展");
		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getExtensions(Class<T> targetClass) {
		Extension extension = extensions.get(targetClass.getName());
		Assert.notNull(extension, "不存在接口" + targetClass.getName() + "的扩展");
		List<T> instances = new ArrayList<>(extension.getBeans().size());
		for (Object value : extension.getBeans().values()) {
			T instance = (T) value;
			instances.add(instance);
		}
		return instances;
	}

	public <T> List<T> getActiveExtensions(Class<T> targetClass) {
		List<T> extensions = getExtensions(targetClass);

		List<T> newExtensions = new ArrayList<>(extensions.size());
		for (T instance : extensions) {
			if (instance.getClass().getAnnotation(Active.class) != null) {
				newExtensions.add(instance);
			}
		}

		Collections.sort(newExtensions, new ActiveComparator());

		return newExtensions;
	}

	public void addExtension(String interfaceName, String name, String className) {
		Extension extension = extensions.get(interfaceName);
		if (extension == null) {
			extension = new Extension();
		}
		extension.addBean(name, ReflectUtils.newInstance(className));
		extensions.put(interfaceName, extension);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, Extension> entry : extensions.entrySet()) {
			builder.append(entry.getKey()).append('\n').append(entry.getValue()).append('\n');
		}

		return builder.toString();
	}

}
