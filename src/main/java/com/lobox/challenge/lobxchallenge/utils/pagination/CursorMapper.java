package com.lobox.challenge.lobxchallenge.utils.pagination;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class CursorMapper {

    private final ObjectMapper objectMapper;


    public String encode(Object value) {
        try {
            byte[] json = objectMapper.writeValueAsBytes(value);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(json);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Could not encode pagination cursor", exception);
        }
    }

    public <T> T decode(String cursor, Class<T> type) {
        if (cursor == null || cursor.isBlank()) {
            return null;
        }
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(cursor);
            return objectMapper.readValue(decoded, type);
        } catch (IllegalArgumentException | IOException exception) {
            throw new IllegalArgumentException("Invalid pagination cursor");
        }
    }
}
