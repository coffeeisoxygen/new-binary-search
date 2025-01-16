package com.coffeecode.model.json;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.model.record.Vocabulary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonServices implements IJsonService {
    private static final Logger logger = LoggerFactory.getLogger(JsonServices.class);
    private final ObjectMapper objectMapper;

    public JsonServices() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Vocabulary> parseJson(String jsonContent) throws JsonParsingException {
        logger.debug("Attempting to parse JSON content");
        
        if (!validateJson(jsonContent)) {
            String message = "Invalid or empty JSON content";
            logger.error(message);
            throw new JsonParsingException(message);
        }

        try {
            List<Vocabulary> vocabularies = objectMapper.readValue(jsonContent,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Vocabulary.class));
            logger.info("Successfully parsed {} vocabulary entries", vocabularies.size());
            return vocabularies;
        } catch (JsonProcessingException e) {
            String message = String.format("Failed to process JSON: %s", e.getMessage());
            logger.error(message, e);
            throw new JsonParsingException(message, e);
        }
    }

    @Override
    public boolean validateJson(String jsonContent) throws JsonParsingException {
        if (jsonContent == null || jsonContent.isBlank()) {
            logger.warn("JSON content is null or empty");
            return false;
        }

        try {
            objectMapper.readTree(jsonContent);
            logger.debug("JSON validation successful");
            return true;
        } catch (JsonProcessingException e) {
            logger.warn("JSON validation failed: {}", e.getMessage());
            return false;
        }
    }
}
