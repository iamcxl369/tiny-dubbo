package com.youku.serialize;

import java.io.Serializable;
import java.util.Arrays;

public class Player implements Serializable {

	private int id;

	private String name;

	private Object[] teammates;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object[] getTeammates() {
		return teammates;
	}

	public void setTeammates(Object[] teammates) {
		this.teammates = teammates;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", teammates=" + Arrays.toString(teammates) + "]";
	}


}
