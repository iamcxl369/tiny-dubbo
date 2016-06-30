package com.youku.rpc.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

import com.youku.rpc.common.ReflectUtils;
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
