package com.youku.cluster.loadbalance;

import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import com.youku.rpc.model.User;

public class TestConsistHash {

	class Node {
		String ip;
		int hash;

		public Node(String ip, int hash) {
			super();
			this.ip = ip;
			this.hash = hash;
		}
	}

	private String[] remotes = { //
			"10.10.10.1", //
			"10.10.10.2", //
			"10.10.10.3" };

	private TreeSet<Node> trees = new TreeSet<>();

	@Before
	public void init() {
		for (String remote : remotes) {
			int hash = System.identityHashCode(remote);
			Node node = new Node(remote, hash);
			trees.add(node);

		}
	}

	@Test
	public void test() {
		User user = new User(1, "jack");

		int hash = System.identityHashCode(user);

		int index = findClosest(hash);

		print("选择" + remotes[index] + "机器");
	}

	private int findClosest(int hash) {
		return 0;
	}

	public void print(Object o) {
		System.out.println(o);
	}
}
