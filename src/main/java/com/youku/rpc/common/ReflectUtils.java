package com.youku.rpc.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.sun.xml.internal.ws.util.StringUtils;

public class ReflectUtils {

	public static <T> T newInstance(Class<T> targetClass) {
		try {
			return targetClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T newInstance(Class<T> targetClass, Class<?>[] argTypes, Object[] args) {
		try {
			return targetClass.getConstructor(argTypes).newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> Field getDeclaredField(Class<T> targetClass, String name) {
		try {
			return targetClass.getDeclaredField(name);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException("无法找到类型" + targetClass + "的属性" + name, e);
		}
	}

	public static <T> Method getDeclaredMethod(Class<T> targetClass, String methodName, Class<?>[] argTypes) {
		try {
			return targetClass.getMethod(methodName, argTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("无法获取到参数为" + Arrays.toString(argTypes) + ",参数名为" + methodName + "的方法", e);
		}
	}

	public static <T> Object invokeMethod(Method method, T instance, Object[] args) {
		try {
			return method.invoke(instance, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("为方法" + method.getName() + "传递的参数" + Arrays.toString(args) + "不匹配", e);
		}
	}

	public static <T> Object invokeMethod(String methodName, T instance, Class<?>[] argTypes, Object[] args) {
		Method method = getDeclaredMethod(instance.getClass(), methodName, argTypes);
		return invokeMethod(method, instance, args);
	}

	public static <T> void invokeSetter(T instance, String name, Class<?>[] argTypes, Object[] args) {
		String setter = "set" + StringUtils.capitalize(name);
		invokeMethod(setter, instance, argTypes, args);
	}

	public static <T> Object invokeGetter(T instance, String name, Class<?>[] argTypes, Object[] args) {
		String getter = "get" + StringUtils.capitalize(name);
		return invokeMethod(getter, instance, argTypes, args);
	}

	public static Field[] getDeclaredFields(Class<?> targetClass) {
		return targetClass.getDeclaredFields();
	}

	public static Method[] getDeclaredMethods(Class<?> targetClass) {
		return targetClass.getDeclaredMethods();
	}

	public static Object getFieldValue(Object target, Field field) {
		try {
			field.setAccessible(true);
			return field.get(target);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("获取" + target + "对象的属性" + field.getName() + "值出现异常", e);
		}
	}

}
