package com.coffeecode.model.json.schema;

import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;

public class JsonSchemaWrapper implements SchemaValidatable {
    private final JsonSchema schema;

    public JsonSchemaWrapper(JsonSchema schema) {
        this.schema = schema;
    }

    @Override
    public Set<ValidationMessage> validate(JsonNode node) {
        return schema.validate(node);
    }
}