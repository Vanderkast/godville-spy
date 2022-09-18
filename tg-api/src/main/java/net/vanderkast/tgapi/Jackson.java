package net.vanderkast.tgapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class Jackson {
    private final Logger logger = LoggerFactory.getLogger(Jackson.class);

    private final ObjectMapper marshaller;

    public static Jackson initialize() {
        var mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new Jackson(mapper);
    }

    public Optional<String> serialize(Object data) {
        try {
            return Optional.of(marshaller.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            logger.error("Json serialization of type {} is failed.", data.getClass(), e);
            return Optional.empty();
        }
    }

    public <T> Optional<T> read(byte[] data, Class<T> clazz) {
        try {
            return Optional.of(marshaller.readValue(data, clazz));
        } catch (IOException e) {
            logger.error("Json parsing failed.", e);
            return Optional.empty();
        }
    }

    public <T> Optional<T> read(byte[] data, TypeReference<T> type) {
        try {
            return Optional.of(marshaller.readValue(data, type));
        } catch (IOException e) {
            logger.error("Json parsing failed.", e);
            return Optional.empty();
        }
    }
}
