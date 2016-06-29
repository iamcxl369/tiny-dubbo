package com.youku.rpc.config;

public class ApplicationConfig {

	private String name;

	private String owner;

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "ApplicationConfig [name=" + name + ", owner=" + owner + "]";
	}

}
