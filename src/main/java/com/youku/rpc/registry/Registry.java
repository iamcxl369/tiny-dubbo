package com.youku.rpc.registry;

import java.util.List;

import com.youku.rpc.remote.URL;

public interface Registry {

	void register(URL url);

	void subscribe();

	List<URL> getServers();
}
