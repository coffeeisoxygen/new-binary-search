package com.coffeecode.model.json.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.core.Vocabulary;
import com.coffeecode.model.json.exception.JsonParsingException;
import com.coffeecode.model.json.exception.JsonValidationException;
import com.coffeecode.model.json.resources.ResourceLoader;
import com.coffeecode.model.json.schema.SchemaValidatable;
import com.coffeecode.model.json.schema.SchemaValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;

public class JsonServices implements IJsonService {

    private final ObjectMapper objectMapper;
    private final SchemaValidator schemaValidator;
    private final AppConfig config;
    private final ResourceLoader resourceLoader;
    private static final Logger logger = LoggerFactory.getLogger(JsonServices.class);

    public JsonServices(AppConfig config,
            ObjectMapper objectMapper,
            SchemaValidatable schema,
            ResourceLoader resourceLoader) {
        this.config = config;
        this.objectMapper = objectMapper;
        this.schemaValidator = new SchemaValidator(schema);
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Vocabulary> parseFile(String filePath) throws JsonParsingException {
        try {
            String path = filePath != null ? filePath : config.getVocabularyPath();
            try (InputStream inputStream = resourceLoader.getResourceAsStream(path)) {
                if (inputStream == null) {
                    throw new JsonParsingException("File not found: " + path);
                }
                String jsonContent = new String(inputStream.readAllBytes());
                return parseJson(jsonContent);
            }
        } catch (IOException e) {
            throw new JsonParsingException("Failed to read file", e);
        }
    }

    @Override
    public boolean isValid(String jsonContent) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonContent);
            Set<ValidationMessage> errors = schemaValidator.validate(jsonNode);
            return errors.isEmpty();
        } catch (JsonProcessingException e) {
            logger.warn("JSON processing failed: {}", e.getMessage());
            return false;
        }
    }

    public List<Vocabulary> parse(String jsonContent) throws JsonParsingException {

        return parseJson(jsonContent);

    }

    private List<Vocabulary> parseJson(String jsonContent) throws JsonParsingException {
        try {
            // Step 1: Parse JSON structure
            JsonNode jsonNode = objectMapper.readTree(jsonContent);

            // Step 2: Validate against schema
            schemaValidator.validate(jsonNode);

            // Step 3: Map to objects
            List<Vocabulary> vocabularies = objectMapper.readValue(jsonContent, objectMapper
                    .getTypeFactory().constructCollectionType(List.class, Vocabulary.class));

            // Step 4: Validate result
            if (vocabularies.isEmpty()) {
                throw new JsonValidationException("No vocabulary entries found");
            }

            logger.info("Parsed {} vocabulary entries", vocabularies.size());
            return vocabularies;

        } catch (JsonProcessingException e) {
            throw new JsonParsingException("Failed to parse JSON content", e);
        }
    }

    // Protected for testing
    protected InputStream getResourceAsStream(String path) {
        return getClass().getResourceAsStream(path);
    }

}
// Todo : extends later
