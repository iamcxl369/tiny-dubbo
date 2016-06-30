package com.youku.rpc.factory;

import com.youku.rpc.net.URL;
import com.youku.rpc.registry.Registry;

public interface RegistryFactory {

	Registry getRegistry(URL url);
}
