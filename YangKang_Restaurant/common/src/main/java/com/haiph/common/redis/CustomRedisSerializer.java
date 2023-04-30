package com.haiph.common.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

public class CustomRedisSerializer implements RedisSerializer<Object> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if (object == null) {
            return new byte[0];
        }

        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize", e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return mapper.readValue(bytes, Object.class);
        } catch (IOException e) {
            throw new SerializationException("Cannot deserialize", e);
        }
    }
}
