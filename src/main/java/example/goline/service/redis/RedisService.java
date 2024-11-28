package example.goline.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

public interface RedisService {
    void set(String key, Object data);

    <T> T get(String key, TypeReference<T> var2);

    <T> T get(String key, Class<T> var2);

    void delete(String key);

    void deletePatternKey(String patternKey);
}

