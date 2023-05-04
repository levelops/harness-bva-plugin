package io.harness.plugins.harness_bva.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JsonUtils {
    private static volatile ObjectMapper objectMapper = null;
    private static final Object lock = new Object();

    public static ObjectMapper get() {
        if (objectMapper != null) {
            return objectMapper;
        }
        synchronized (lock) {
            if (objectMapper != null) {
                return objectMapper;
            }
            objectMapper = buildObjectMapper();
            return objectMapper;
        }
    }

    public static ObjectMapper buildObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Don't throw an exception when json has extra fields you are
        // not serializing on. This is useful when you want to use a pojo
        // for deserialization and only care about a portion of the json
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return mapper;
    }


}
