package com.youku.rpc.remote.serialize.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youku.rpc.remote.serialize.Serializer;

public class JacksonSerializer implements Serializer {

	private static final Logger log = LoggerFactory.getLogger(JacksonSerializer.class);

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public byte[] serialize(Object obj) {
		log.info("采用jackson序列");
		try {
			return mapper.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> targetClass) {
		log.info("采用jackson反序列");
		try {
			return mapper.readValue(data, targetClass);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object deserialize(byte[] data) {
		return deserialize(data, Object.class);
	}

}
