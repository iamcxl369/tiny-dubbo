package com.youku.rpc.bootstrap;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.Assert;

import com.youku.rpc.common.Const;
import com.youku.rpc.common.ReflectUtils;
import com.youku.rpc.config.ApplicationConfig;
import com.youku.rpc.config.ProtocolConfig;
import com.youku.rpc.config.ReferenceConfig;
import com.youku.rpc.config.RegistryConfig;
import com.youku.rpc.config.ServiceConfig;

public class XmlParser {

	private Map<String, Object> context;

	private List<ServiceConfig<Object>> serviceConfigs;

	private List<ReferenceConfig<Object>> referenceConfigs;

	private List<ProtocolConfig> protocolConfigs;

	public XmlParser(String configLocation) {
		parse(configLocation);
	}

	private void parse(String configLocation) {
		context = new HashMap<>();
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File(configLocation));
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}

		Element root = document.getRootElement();

		ApplicationConfig applicationConfig = parseApplication(root.element("application"));
		RegistryConfig registryConfig = parseRegistry(root.element("registry"));

		protocolConfigs = parseProtocols(root.elements("protocol"));

		serviceConfigs = parseServices(root.elements("service"), applicationConfig, registryConfig, protocolConfigs);

		referenceConfigs = parseReferences(root.elements("reference"), applicationConfig, registryConfig);
	}

	public void execute() {
		for (ServiceConfig<Object> serviceConfig : serviceConfigs) {
			serviceConfig.export();
		}

		for (ReferenceConfig<Object> referenceConfig : referenceConfigs) {
			String id = referenceConfig.getAttachment(Const.ID_KEY);

			Assert.notNull(id, "reference标签中id不能为空");
			context.put(id, referenceConfig.get());
		}
	}

	private <T> List<ReferenceConfig<T>> parseReferences(List<?> elements, ApplicationConfig applicationConfig,
			RegistryConfig registryConfig) {
		List<ReferenceConfig<T>> referenceConfigs = new ArrayList<>(elements.size());
		for (Object obj : elements) {
			Element element = (Element) obj;

			ReferenceConfig<T> referenceConfig = parseReference(element, applicationConfig, registryConfig);

			referenceConfigs.add(referenceConfig);
		}

		return referenceConfigs;
	}

	private <T> ReferenceConfig<T> parseReference(Element element, ApplicationConfig applicationConfig,
			RegistryConfig registryConfig) {
		ReferenceConfig<T> referenceConfig = new ReferenceConfig<>();
		referenceConfig.setApplicationConfig(applicationConfig);
		referenceConfig.setRegistryConfig(registryConfig);

		for (Object obj : element.attributes()) {
			Attribute attribute = (Attribute) obj;
			referenceConfig.addAttachment(attribute.getName(), attribute.getValue());
		}

		return referenceConfig;
	}

	private <T> List<ServiceConfig<T>> parseServices(List<?> elements, ApplicationConfig applicationConfig,
			RegistryConfig registryConfig, List<ProtocolConfig> protocolConfigs) {
		List<ServiceConfig<T>> serviceConfigs = new ArrayList<>(elements.size());
		for (Object obj : elements) {
			Element element = (Element) obj;

			ServiceConfig<T> serviceConfig = parseService(element, applicationConfig, registryConfig, protocolConfigs);

			serviceConfigs.add(serviceConfig);
		}

		return serviceConfigs;
	}

	@SuppressWarnings("unchecked")
	private <T> ServiceConfig<T> parseService(Element element, ApplicationConfig applicationConfig,
			RegistryConfig registryConfig, List<ProtocolConfig> protocolConfigs) {
		ServiceConfig<T> serviceConfig = new ServiceConfig<>();

		serviceConfig.setInterfaceClass((Class<T>) ReflectUtils.forName(element.attributeValue("interface")));

		String strWeight = element.attributeValue("weight");
		if (strWeight != null) {
			serviceConfig.setWeight(Integer.parseInt(strWeight));
		}

		T instance = ReflectUtils.newInstance(element.attributeValue("class"));

		serviceConfig.setRef(instance);
		serviceConfig.setApplicationConfig(applicationConfig);
		serviceConfig.setRegistryConfig(registryConfig);

		String protocol = element.attributeValue("protocol");

		ProtocolConfig protocolConfig = searchProtocol(protocol, protocolConfigs);

		Assert.notNull(protocolConfig, "找不到name为" + protocol + "的protocol");

		serviceConfig.setProtocolConfig(protocolConfig);
		return serviceConfig;
	}

	private ProtocolConfig searchProtocol(String name, List<ProtocolConfig> protocolConfigs) {
		for (ProtocolConfig protocolConfig : protocolConfigs) {
			if (StringUtils.equals(name, protocolConfig.getName())) {
				return protocolConfig;
			}
		}
		return null;
	}

	private List<ProtocolConfig> parseProtocols(List<?> elements) {
		List<ProtocolConfig> protocolConfigs = new ArrayList<>(elements.size());
		for (Object obj : elements) {
			Element element = (Element) obj;
			ProtocolConfig config = parseProtocol(element);
			protocolConfigs.add(config);
		}
		return protocolConfigs;
	}

	private ProtocolConfig parseProtocol(Element element) {
		ProtocolConfig config = new ProtocolConfig();
		String ip = element.attributeValue("ip");
		if (ip != null) {
			config.setIp(ip);
		}

		String serializer = element.attributeValue("serializer");
		if (serializer != null) {
			config.setSerializer(serializer);
		}

		config.setName(element.attributeValue("name"));
		config.setPort(Integer.parseInt(element.attributeValue("port")));
		return config;
	}

	private RegistryConfig parseRegistry(Element element) {
		Assert.notNull(element, "缺少<registry><registry/>标签");
		return new RegistryConfig(element.attributeValue("address"));
	}

	private ApplicationConfig parseApplication(Element element) {
		Assert.notNull(element, "缺少<application></application>标签");
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(element.attributeValue("name"));
		applicationConfig.setOwner(element.attributeValue("owner"));
		return applicationConfig;
	}

	public Map<String, Object> getContext() {
		return context;
	}

	public void setContext(Map<String, Object> context) {
		this.context = context;
	}

	public void close() {
		context = null;
		serviceConfigs = null;
		referenceConfigs = null;
		protocolConfigs = null;
	}

}
