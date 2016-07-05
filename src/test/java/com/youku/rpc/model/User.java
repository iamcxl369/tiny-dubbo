package com.youku.rpc.model;

import java.io.Serializable;

public class User implements Serializable {

	private int id;

	private String name;

	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			if (user.id == 0 || user.name == null) {
				return false;
			} else {
				return id == user.id && user.name.equals(name);
			}
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}
