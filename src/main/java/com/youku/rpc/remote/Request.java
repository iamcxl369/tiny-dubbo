package com.youku.rpc.remote;

import java.io.Serializable;
import java.util.Arrays;

import com.youku.rpc.common.ReflectUtils;

public class Request implements Serializable {

	private static final long serialVersionUID = 8974769973791263295L;

	private Class<?> interfaceClass;

	private String interfaceName;

	private transient Object ref;

	private String methodName;

	private Class<?>[] argumentTypes;

	private Object[] arguments;

	public Object invoke() {
		return ReflectUtils.invokeMethod(methodName, ref, argumentTypes, arguments);
	}

	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<?> interfaceClass) {
		this.interfaceClass = interfaceClass;
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

	public String getInterfaceName() {
		return interfaceName;
	}

	@Override
	public String toString() {
		return "Request [interfaceClass=" + interfaceClass + ", ref=" + ref + ", methodName=" + methodName
				+ ", argumentTypes=" + Arrays.toString(argumentTypes) + ", arguments=" + Arrays.toString(arguments)
				+ "]";
	}

}
