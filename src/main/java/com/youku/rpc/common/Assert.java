//package com.youku.rpc.common;
//
//import java.util.Collection;
//import java.util.Map;
//
//public class Assert {
//
//	public static void equal(String s1, String s2) {
//		
//	}
//
//	public static void isTrue(boolean expression, String message) {
//		if (!expression) {
//			throw new IllegalArgumentException(message);
//		}
//	}
//
//	public static void isTrue(boolean expression) {
//		isTrue(expression, "[Assertion failed] - this expression must be true");
//	}
//
//	public static void isNull(Object object, String message) {
//		if (object != null) {
//			throw new IllegalArgumentException(message);
//		}
//	}
//
//	public static void isNull(Object object) {
//		isNull(object, "[Assertion failed] - the object argument must be null");
//	}
//
//	public static void notNull(Object object, String message) {
//		if (object == null) {
//			throw new IllegalArgumentException(message);
//		}
//	}
//
//	public static void notNull(Object object) {
//		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
//	}
//
//	public static void notEmpty(Object[] array, String message) {
//		if (array == null || array.length == 0) {
//			throw new IllegalArgumentException(message);
//		}
//	}
//
//	public static void notEmpty(Object[] array) {
//		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
//	}
//
//	public static void noNullElements(Object[] array, String message) {
//		if (array != null) {
//			for (Object element : array) {
//				if (element == null) {
//					throw new IllegalArgumentException(message);
//				}
//			}
//		}
//	}
//
//	public static void noNullElements(Object[] array) {
//		noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
//	}
//
//	public static void notEmpty(Collection<?> collection, String message) {
//		if (collection == null || collection.isEmpty()) {
//			throw new IllegalArgumentException(message);
//		}
//	}
//
//	public static void notEmpty(Collection<?> collection) {
//		notEmpty(collection,
//				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
//	}
//
//	public static void notEmpty(Map<?, ?> map, String message) {
//		if (map == null || map.isEmpty()) {
//			throw new IllegalArgumentException(message);
//		}
//	}
//
//	public static void notEmpty(Map<?, ?> map) {
//		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
//	}
//
//}
