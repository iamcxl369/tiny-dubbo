package com.youku.rpc.remote.serialize.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.remote.serialize.Serializer;

public class JavaSerializer implements Serializer {

	private static final Logger log = LoggerFactory.getLogger(JavaSerializer.class);

	@Override
	public byte[] serialize(Object obj) {
		log.info("采用java序列化");
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(bos)) {
			os.writeObject(obj);
			os.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] data, Class<T> targetClass) {
		log.info("采用java反序列化");
		try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(bis)) {
			return (T) is.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object deserialize(byte[] data) {
		log.info("采用java反序列化");
		try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(bis)) {
			return is.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
