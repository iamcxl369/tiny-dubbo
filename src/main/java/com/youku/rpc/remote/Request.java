package com.youku.rpc.remote;

import java.util.Arrays;

import com.youku.rpc.common.ReflectUtils;

public class Request {

	private String interfaceName;

	private Object ref;

	private String methodName;

	private Class<?>[] argumentTypes;

	private Object[] arguments;

	public Object invoke() {
		return ReflectUtils.invokeMethod(methodName, ref, argumentTypes, arguments);
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Object getRef() {
		return ref;
	}

	public void setRef(Object ref) {
		this.ref = ref;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	public Class<?>[] getArgumentTypes() {
		return argumentTypes;
	}

	public void setArgumentTypes(Class<?>[] argumentTypes) {
		this.argumentTypes = argumentTypes;
	}

	@Override
	public String toString() {
		return "Request [interfaceName=" + interfaceName + ", ref=" + ref + ", methodName=" + methodName
				+ ", argumentTypes=" + Arrays.toString(argumentTypes) + ", arguments=" + Arrays.toString(arguments)
				+ "]";
	}

}
