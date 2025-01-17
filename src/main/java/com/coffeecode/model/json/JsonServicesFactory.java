package com.coffeecode.model.json;

import com.coffeecode.config.AppConfig;
import com.coffeecode.model.json.resources.ClassPathResourceLoader;
import com.coffeecode.model.json.resources.ResourceLoader;
import com.coffeecode.model.json.schema.DefaultSchemaLoader;
import com.coffeecode.model.json.schema.JsonSchemaWrapper;
import com.coffeecode.model.json.schema.SchemaLoader;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;

public class JsonServicesFactory {

    private JsonServicesFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static JsonServices create(AppConfig config) {
        ResourceLoader resourceLoader = new ClassPathResourceLoader();
        SchemaLoader schemaLoader = new DefaultSchemaLoader(resourceLoader);
        ObjectMapper objectMapper = createObjectMapper();
        JsonSchema schema = schemaLoader.loadSchema(config.getSchemaPath());

        return new JsonServices(
            config, 
            objectMapper, 
            new JsonSchemaWrapper(schema), 
            resourceLoader
        );
    }

    private static ObjectMapper createObjectMapper() {
        return new ObjectMapper()
                .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
                .enable(JsonParser.Feature.ALLOW_COMMENTS)
                .enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
    }
}
