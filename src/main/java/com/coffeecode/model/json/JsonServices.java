package com.coffeecode.model.json;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class JsonServices {

    private static final Logger logger = LoggerFactory.getLogger(JsonServices.class);
    private final ObjectMapper objectMapper;
    private final JsonSchema schema;

    public JsonServices() {
        this.objectMapper = new ObjectMapper()
                .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
                .enable(JsonParser.Feature.ALLOW_COMMENTS)
                .enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            InputStream schemaStream = getClass().getResourceAsStream("/schema/vocabulary-schema.json");
            this.schema = factory.getSchema(schemaStream);
        } catch (Exception e) {
            logger.error("Failed to load JSON schema: {}", e.getMessage());
            throw new IllegalStateException("Failed to load JSON schema", e);
        }
    }

    public List<Vocabulary> parseFile(String filePath) throws JsonParsingException {
        try {
            String jsonContent = Files.readString(Path.of(filePath));
            return parseJson(jsonContent);
        } catch (IOException e) {
            String message = String.format("Failed to read file %s: %s", filePath, e.getMessage());
            logger.error(message, e);
            throw new JsonParsingException(message, e);
        }
    }

    private List<Vocabulary> parseJson(String jsonContent) throws JsonParsingException {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonContent);
            validateJsonContent(jsonNode);

            List<Vocabulary> vocabularies = objectMapper.readValue(jsonContent,
                    objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, Vocabulary.class));

            logger.info("Successfully parsed {} vocabulary entries", vocabularies.size());
            return vocabularies;

        } catch (JsonProcessingException e) {
            logger.error("JSON parsing failed: {}", e.getMessage());
            throw new JsonParsingException("Failed to parse JSON", e);
        }
    }

    private void validateJsonContent(JsonNode jsonNode) throws JsonValidationException {
        Set<ValidationMessage> validationMessages = schema.validate(jsonNode);
        if (!validationMessages.isEmpty()) {
            String errors = validationMessages.stream()
                    .map(ValidationMessage::getMessage)
                    .collect(Collectors.joining(", "));
            throw new JsonValidationException("Schema validation failed: " + errors);
        }
    }
}
