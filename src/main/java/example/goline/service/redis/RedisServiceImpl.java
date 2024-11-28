package example.goline.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImpl implements RedisService{
    private final RedisTemplate<Object, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void set(String key, Object data) {
        try {
            String stringifyData = objectMapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(key, stringifyData);
        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage());
        }
    }

    @Override
    public <T> T get(String key, Class<T> var2) {
        try {
            String data = (String) redisTemplate.opsForValue().get(key);
            if(Objects.isNull(data)) return null;
            return objectMapper.readValue(data, var2);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public <T> T get(String key, TypeReference<T> var2) {
        try {
            String data = (String) redisTemplate.opsForValue().get(key);
            if(Objects.isNull(data)) return null;
            return objectMapper.readValue(data, var2);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void deletePatternKey(String patternKey) {
        Set<Object> setKeys = redisTemplate.keys(patternKey);
        redisTemplate.delete(setKeys);
    }
}

