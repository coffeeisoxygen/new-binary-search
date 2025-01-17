package com.coffeecode.model.json;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.Vocabulary;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

public class JsonServices implements IJsonService {

    private static final Logger logger = LoggerFactory.getLogger(JsonServices.class);
    private final ObjectMapper objectMapper;
    private final JsonSchema schema;
    private final AppConfig config;

    public JsonServices(AppConfig config) {
        this.config = config;
        this.objectMapper = configureObjectMapper();
        this.schema = loadSchema();
    }

    public JsonServices() {
        this(AppConfig.getDefault());
    }

    @Override
    public List<Vocabulary> parseFile(String filePath) throws JsonParsingException {
        String path = filePath != null ? filePath : config.getVocabularyPath();
        try {
            logger.debug("Reading file: {}", path);
            InputStream inputStream = getResourceAsStream(path);
            if (inputStream == null) {
                throw new JsonParsingException("File not found in resources: " + path);
            }
            String jsonContent = new String(inputStream.readAllBytes());
            return parseJson(jsonContent);
        } catch (NoSuchFileException e) {
            throw new JsonParsingException("File not found: " + path, e);
        } catch (IOException e) {
            throw new JsonParsingException("IO error reading " + path, e);
        }
    }

    @Override
    public boolean isValid(String jsonContent) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonContent);
            Set<ValidationMessage> errors = schema.validate(jsonNode);
            return errors.isEmpty();
        } catch (JsonProcessingException e) {
            logger.warn("JSON processing failed: {}", e.getMessage());
            return false;
        }
    }

    private JsonSchema loadSchema() {
        try {
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            String schemaPath = config.getSchemaPath();
            // Use getResourceAsStream with leading slash
            InputStream schemaStream = getResourceAsStream(schemaPath);

            if (schemaStream == null) {
                throw new JsonParsingException("Schema not found in classpath: " + schemaPath);
            }

            return factory.getSchema(schemaStream);
        } catch (Exception e) {
            logger.error("Failed to load JSON schema: {}", e.getMessage());
            throw new JsonParsingException("Failed to load JSON schema", e);
        }
    }

    private ObjectMapper configureObjectMapper() {
        return new ObjectMapper().enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
                .enable(JsonParser.Feature.ALLOW_COMMENTS)
                .enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
    }

    List<Vocabulary> parseJson(String jsonContent) throws JsonParsingException {
        try {
            // Step 1: Parse JSON structure
            JsonNode jsonNode = objectMapper.readTree(jsonContent);

            // Step 2: Validate against schema
            validateSchema(jsonNode);

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
            throw new JsonParsingException("Failed to parse JSON: " + e.getMessage(), e);
        }
    }

    private void validateSchema(JsonNode jsonNode) throws JsonValidationException {
        Set<ValidationMessage> errors = schema.validate(jsonNode);
        if (!errors.isEmpty()) {
            String errorMessages = errors.stream().map(ValidationMessage::getMessage)
                    .collect(Collectors.joining("; "));
            throw new JsonValidationException("Schema validation failed: " + errorMessages);
        }
    }

    // Protected for testing
    protected InputStream getResourceAsStream(String path) {
        return getClass().getResourceAsStream(path);
    }

}
// Todo : extends later
