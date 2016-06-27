package com.youku.rpc.registry;

import com.youku.rpc.net.URL;

public interface RegistryService {

	void register(URL url);

	void subscribe();
}
