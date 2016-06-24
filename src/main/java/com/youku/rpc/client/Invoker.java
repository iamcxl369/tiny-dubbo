package com.youku.rpc.client;

public class Invoker {
	
	private Client client;
	
	private Class<?> interfaceClass;
	
	private ClassLoader interfaceClassLoader;

	public Invoker(Client client, Class<?> interfaceClass) {
		this.client=client;
		this.interfaceClass=interfaceClass;
		this.interfaceClassLoader=interfaceClass.getClassLoader();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(Class<?> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	public ClassLoader getInterfaceClassLoader() {
		return interfaceClassLoader;
	}

	public void setInterfaceClassLoader(ClassLoader interfaceClassLoader) {
		this.interfaceClassLoader = interfaceClassLoader;
	}

}
