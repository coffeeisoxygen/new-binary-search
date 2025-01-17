package com.coffeecode.model.json.schema;

import java.io.InputStream;

import com.coffeecode.model.json.exception.JsonParsingException;
import com.coffeecode.model.json.resources.ResourceLoader;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

public class DefaultSchemaLoader implements SchemaLoader {

    private final ResourceLoader resourceLoader;

    public DefaultSchemaLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public JsonSchema loadSchema(String schemaPath) {
        try {
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            try (InputStream schemaStream = resourceLoader.getResourceAsStream(schemaPath)) {
                if (schemaStream == null) {
                    throw new JsonParsingException("Schema not found: " + schemaPath);
                }
                return factory.getSchema(schemaStream);
            }
        } catch (Exception e) {
            throw new JsonParsingException("Failed to load schema: " + e.getMessage(), e);
        }
    }
}
