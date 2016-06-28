package com.youku.rpc.registry;

import java.util.List;

import com.youku.rpc.net.URL;

public interface RegistryService {

	void register(URL url);

	void subscribe();

	List<URL> getServers();
}
